package com.jebytek.file.controller.admin;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jebytek.file.config.FileApplication;
import com.jebytek.server.dto.FileDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.enums.FileUseEnum;
import com.jebytek.server.service.FileService;
import com.jebytek.server.util.Base64ToMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(FileApplication.class);
    private static final String BUSINESS_NAME = "UPLOAD_FILE";

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${amzs3.bucketName}")
    private String bucketName;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {
        LOG.info("Start uploading shard...");

        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // save shard to local
        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        // if directory does not exist, then create
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdirs();
        }

        String path = new StringBuffer(dir)
                .append(File.separator)
                .append(key)
                .append(".")
                .append(suffix).toString(); // eg: course/2uIOLxr8kACgESC8mg4sAw.mp4
        String localPath = new StringBuilder(path)
                .append(".")
                .append(fileDto.getShardIndex()).toString(); // eg: course/2uIOLxr8kACgESC8mg4sAw.mp4.0

        String fullPath = FILE_PATH + localPath;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("Saving shard record:");
        fileDto.setPath(path);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);

        if (fileDto.getShardIndex().equals(fileDto.getShardTotal() - 1)) {
            this.merge(fileDto);
        }

        return responseDto;
    }

    public void merge(FileDto fileDto) throws Exception {
        LOG.info("Shards Merging Starts...");
        String path = fileDto.getPath().replace(FILE_DOMAIN, "");
        int shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + path);
        FileOutputStream fileOutputStream = new FileOutputStream(newFile, true); // support appending data
        FileInputStream fileInputStream = null;
        byte[] buffer = new byte[10 * 1024 * 1024];
        int len;

        try {
            for (int i = 0; i < shardTotal; i++) {
                fileInputStream = new FileInputStream(new File(FILE_PATH + path + "." + i));
                while ((len = fileInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            LOG.error("Shard Merging Error", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                fileOutputStream.close();
                LOG.info("IO Stream Closed.");
            } catch (Exception e) {
                LOG.error("IO Stream Closing Error", e);
            }
        }
        LOG.info("Shards Merging Completed.");


        // merging completed, then delete shards
        System.gc();
        Thread.sleep(100);

        // transfer merged file to S3
        try {
            transferToS3(path);
        } catch (IOException e) {
            LOG.error("S3 Transfer Error ", e);
        }

        LOG.info("Start Deleting Shards...");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + i;
            File file = new File(filePath);
            boolean result = file.delete();
            LOG.info("Delete{} -- {}", filePath, result ? "SUCCESS" : "FAIL");
        }
        LOG.info("Shards Deleted.");
    }

    private void transferToS3(String path) throws Exception {
        Regions clientRegion = Regions.US_EAST_2;
        String fileObjKeyName = path;
        String fileName = FILE_PATH + path;

        try {
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();


            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest("jebytek", fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    @GetMapping("/check/{key}")
    public ResponseDto check(@PathVariable String key) {
        LOG.info("Start Checking Shards: {}", key);
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = fileService.findByKey(key);
        if (fileDto != null) {
            fileDto.setPath(FILE_DOMAIN + fileDto.getPath());
        }
        responseDto.setContent(fileDto);
        return responseDto;
    }
}
