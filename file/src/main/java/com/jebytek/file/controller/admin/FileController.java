package com.jebytek.file.controller.admin;

import com.jebytek.server.dto.FileDto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.service.FileService;
import com.jebytek.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/file")
public class FileController {
    @Resource
    private FileService fileService;

    /*
    * retrieve all files
    * */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        fileService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /*
    * save a record
    * when id is empty, insert;
    * when id is not empty, update
    * */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody FileDto fileDto) {

        // saving validation
        ValidatorUtil.require(fileDto.getPath(), "Relative Path");
        ValidatorUtil.length(fileDto.getPath(), "Relative Path", 1, 100);
        ValidatorUtil.length(fileDto.getName(), "File Name", 1, 100);
        ValidatorUtil.length(fileDto.getSuffix(), "File Extension", 1, 10);

        ResponseDto responseDto = new ResponseDto();
        fileService.save(fileDto);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    /*
    * delete
    * */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        fileService.delete(id);
        return responseDto;
    }
}
