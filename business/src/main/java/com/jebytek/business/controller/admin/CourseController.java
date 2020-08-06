package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.CourseDto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.service.CourseService;
import com.jebytek.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    /*
    * retrieve all courses
    * */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /*
    * save a record
    * when id is empty, insert;
    * when id is not empty, update
    * */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {

        // saving validation
        ValidatorUtil.require(courseDto.getName(), "Name");
        ValidatorUtil.length(courseDto.getName(), "Name", 1, 50);
        ValidatorUtil.length(courseDto.getDescription(), "Description", 1, 2000);
        ValidatorUtil.length(courseDto.getThumb(), "Thumb", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /*
    * delete
    * */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseService.delete(id);
        return responseDto;
    }
}
