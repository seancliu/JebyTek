package com.jebytek.file.controller.admin;

import com.jebytek.file.config.FileApplication;
import com.jebytek.server.dto.FileDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.enums.FileUseEnum;
import com.jebytek.server.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile shard,
                              String use,
                              String name,
                              String suffix,
                              Integer size,
                              Integer shardIndex,
                              Integer shardSize,
                              Integer shardTotal,
                              String key) throws IOException {
        LOG.info("Start uploading shard...");

        // save shard to local
        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        // if directory does not exist, then create
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdirs();
        }

        String path = dir + File.separator + key + "." + suffix;
        String fullPath = FILE_PATH + path;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("Saving shard record:");
        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(name);
        fileDto.setSize(Math.toIntExact(shard.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse(use);
        fileDto.setShardIndex(shardIndex);
        fileDto.setShardSize(shardSize);
        fileDto.setShardTotal(shardTotal);
        fileDto.setKey(key);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    @GetMapping("/merge")
    public ResponseDto merge() throws Exception {
        File newFile = new File(FILE_PATH + "/course/merge_test.mp4");
        FileOutputStream fileOutputStream = new FileOutputStream(newFile, true); // support appending data
        FileInputStream fileInputStream = null;
        byte[] buffer = new byte[10 * 1024 * 1024];
        int len;

        try {
            // read 1st shard
            fileInputStream = new FileInputStream(new File(FILE_PATH + "/course/wrAIwDHQ.blob"));
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }

            // read 2nd shard
            fileInputStream = new FileInputStream(new File(FILE_PATH + "/course/d556nXPq.blob"));
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }

            // read 3rd shard
            fileInputStream = new FileInputStream(new File(FILE_PATH + "/course/J3GUPmis.blob"));
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
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
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
