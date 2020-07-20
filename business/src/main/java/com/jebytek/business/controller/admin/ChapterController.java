package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.ChapterDto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @RequestMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto) {
        chapterService.list(pageDto);
        return pageDto;
    }

    @RequestMapping("/save")
    public ChapterDto save(@RequestBody ChapterDto chapterDto) {
        chapterService.save(chapterDto);
        return chapterDto;
    }
}
