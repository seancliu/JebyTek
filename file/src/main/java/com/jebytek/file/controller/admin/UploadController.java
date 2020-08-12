package com.jebytek.file.controller.admin;

import com.jebytek.file.config.FileApplication;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(FileApplication.class);
    private static final String BUSINESS_NAME = "UPLOAD_FILE";

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("Start uploading file: {}", file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        // save file to local
        String fileName = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid(); // append uuid to avoid collision
        String fullPath = "D:/jebytek/upload/instructor/" + key + "-" + fileName;
        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
