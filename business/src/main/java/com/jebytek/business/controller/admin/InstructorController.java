package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.InstructorDto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.service.InstructorService;
import com.jebytek.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/instructor")
public class InstructorController {
    @Resource
    private InstructorService instructorService;

    /*
    * retrieve all instructors
    * */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        instructorService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /*
    * save a record
    * when id is empty, insert;
    * when id is not empty, update
    * */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody InstructorDto instructorDto) {

        // saving validation
        ValidatorUtil.require(instructorDto.getName(), "Name");
        ValidatorUtil.length(instructorDto.getName(), "Name", 1, 50);
        ValidatorUtil.length(instructorDto.getAlias(), "Alias", 1, 50);
        ValidatorUtil.length(instructorDto.getAvatar(), "Avatar", 1, 100);
        ValidatorUtil.length(instructorDto.getTitle(), "Title", 1, 50);
        ValidatorUtil.length(instructorDto.getMotto(), "Motto", 1, 50);
        ValidatorUtil.length(instructorDto.getIntro(), "Intro", 1, 500);

        ResponseDto responseDto = new ResponseDto();
        instructorService.save(instructorDto);
        responseDto.setContent(instructorDto);
        return responseDto;
    }

    /*
    * delete
    * */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        instructorService.delete(id);
        return responseDto;
    }
}
