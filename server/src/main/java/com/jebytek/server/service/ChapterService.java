package com.jebytek.server.service;

import com.jebytek.server.domain.Chapter;
import com.jebytek.server.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;
    public List<Chapter> list() {
        return chapterMapper.selectByExample(null);
    }
}
