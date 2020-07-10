package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.ChapterDto;
import com.jebytek.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @RequestMapping("/chapter")
    public List<ChapterDto> chapter() {
        return chapterService.list();
    }
}
