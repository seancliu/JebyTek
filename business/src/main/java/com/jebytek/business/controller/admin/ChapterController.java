package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.ChapterDto;
import com.jebytek.server.dto.ChapterPageDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.service.ChapterService;
import com.jebytek.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    /*
    * retrieve all chapters
    * */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody ChapterPageDto chapterPageDto) {
        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(chapterPageDto.getCourseId(), "Course ID");
        chapterService.list(chapterPageDto);
        responseDto.setContent(chapterPageDto);
        return responseDto;
    }

    /*
    * save a record
    * when id is empty, insert;
    * when id is not empty, update
    * */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto) {

        // saving validation
        ValidatorUtil.require(chapterDto.getName(), "Course name");
        ValidatorUtil.require(chapterDto.getCourseId(), "Course ID");
        ValidatorUtil.length(chapterDto.getCourseId(), "Course ID", 1, 8);

        ResponseDto responseDto = new ResponseDto();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    /*
    * delete
    * */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(id);
        return responseDto;
    }
}
