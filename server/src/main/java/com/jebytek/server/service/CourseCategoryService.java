package com.jebytek.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jebytek.server.domain.CourseCategory;
import com.jebytek.server.domain.CourseCategoryExample;
import com.jebytek.server.dto.CategoryDto;
import com.jebytek.server.dto.CourseCategoryDto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.mapper.CourseCategoryMapper;
import com.jebytek.server.util.CopyUtil;
import com.jebytek.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseCategoryService {
    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    /*
    * retrieve courseCategorys
    * */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto> courseCategoryDtoList = new ArrayList<>();
        for (int i = 0, size = courseCategoryList.size(); i < size; i++) {
            CourseCategory courseCategory = courseCategoryList.get(i);
            CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
            BeanUtils.copyProperties(courseCategory, courseCategoryDto);
            courseCategoryDtoList.add(courseCategoryDto);
        }
        pageDto.setList(courseCategoryList);
    }

    /*
     * save a record
     * when id is empty, insert;
     * when id is not empty, update
     * */
    public void save(CourseCategoryDto courseCategoryDto) {
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto, CourseCategory.class);
        if (StringUtils.isEmpty(courseCategoryDto.getId())) {
            this.insert(courseCategory);
        } else {
            this.update(courseCategory);
        }
    }

    private void insert(CourseCategory courseCategory) {
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }

    private void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    public void saveBatch(String courseId, List<CategoryDto> dtoList) {
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);
        for (int i = 0, size = dtoList.size(); i < size; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDto.getId());
            insert(courseCategory);
        }
    }
}
