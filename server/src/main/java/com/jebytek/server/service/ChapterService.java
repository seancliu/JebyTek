package com.jebytek.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jebytek.server.domain.Chapter;
import com.jebytek.server.domain.ChapterExample;
import com.jebytek.server.dto.ChapterDto;
import com.jebytek.server.dto.ChapterPageDto;
import com.jebytek.server.mapper.ChapterMapper;
import com.jebytek.server.util.CopyUtil;
import com.jebytek.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;

    /*
    * retrieve chapters
    * */
    public void list(ChapterPageDto chapterPageDto) {
        PageHelper.startPage(chapterPageDto.getPage(), chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if (!StringUtils.isEmpty(chapterPageDto.getCourseId())) {
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        chapterPageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        for (int i = 0, size = chapterList.size(); i < size; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
        }
        chapterPageDto.setList(chapterList);
    }

    /*
     * save a record
     * when id is empty, insert;
     * when id is not empty, update
     * */
    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())) {
            this.insert(chapter);
        } else {
            this.update(chapter);
        }
    }

    private void insert(Chapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
}
