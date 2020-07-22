package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.ChapterDto;
import com.jebytek.server.dto.PageDto;
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

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        chapterService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(id);
        return responseDto;
    }
}
