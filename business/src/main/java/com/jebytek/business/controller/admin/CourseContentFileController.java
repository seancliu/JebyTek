package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.CourseContentFileDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.service.CourseContentFileService;
import com.jebytek.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course-content-file")
public class CourseContentFileController {
    @Resource
    private CourseContentFileService courseContentFileService;

    /*
    * retrieve all courseContentFiles
    * */
    @GetMapping("/list/{courseId}")
    public ResponseDto list(@PathVariable String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseContentFileDto> fileDtoList = courseContentFileService.list(courseId);
        responseDto.setContent(fileDtoList);
        return responseDto;
    }

    /*
    * save a record
    * when id is empty, insert;
    * when id is not empty, update
    * */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseContentFileDto courseContentFileDto) {

        // saving validation
        ValidatorUtil.require(courseContentFileDto.getCourseId(), "Course ID");
        ValidatorUtil.length(courseContentFileDto.getUrl(), "URL", 1, 100);
        ValidatorUtil.length(courseContentFileDto.getName(), "File Name", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        courseContentFileService.save(courseContentFileDto);
        responseDto.setContent(courseContentFileDto);
        return responseDto;
    }

    /*
    * delete
    * */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseContentFileService.delete(id);
        return responseDto;
    }
}
