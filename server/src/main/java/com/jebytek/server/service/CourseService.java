package com.jebytek.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jebytek.server.domain.Course;
import com.jebytek.server.domain.CourseContent;
import com.jebytek.server.domain.CourseExample;
import com.jebytek.server.dto.CourseContentDto;
import com.jebytek.server.dto.CourseDto;
import com.jebytek.server.dto.IdxDto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.mapper.CourseContentMapper;
import com.jebytek.server.mapper.CourseMapper;
import com.jebytek.server.mapper.customized.MyCourseMapper;
import com.jebytek.server.util.CopyUtil;
import com.jebytek.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private MyCourseMapper myCourseMapper;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;

    /*
    * retrieve courses
    * */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        courseExample.setOrderByClause("idx asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (int i = 0, size = courseList.size(); i < size; i++) {
            Course course = courseList.get(i);
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto);
            courseDtoList.add(courseDto);
        }
        pageDto.setList(courseList);
    }

    /*
     * save a record
     * when id is empty, insert;
     * when id is not empty, update
     * */
    @Transactional
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);
        if (StringUtils.isEmpty(courseDto.getId())) {
            this.insert(course);
        } else {
            this.update(course);
        }

        // save categories
        courseCategoryService.saveBatch(course.getId(), courseDto.getCategorys());
    }

    private void insert(Course course) {
        Date now = new Date();
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }

    private void update(Course course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }

    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    public void updateTime(String courseId) {
        myCourseMapper.updateTime(courseId);
    }

    /**
     * Search course content
     */
    public CourseContentDto searchContent(String id) {
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if (content == null) {
            return null;
        }
        return CopyUtil.copy(content, CourseContentDto.class);
    }

    /**
     * Save course content (Insert & Update)
     */
    public int saveContent(CourseContentDto contentDto) {
        CourseContent content = CopyUtil.copy(contentDto, CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(content);
        if (i == 0) {
            i = courseContentMapper.insert(content);
        }
        return i;
    }

    /**
     * Edit Index
     * @param idxDto
     */
    @Transactional
    public void idx(IdxDto idxDto) {
        // Update current index
        myCourseMapper.updateIdx(idxDto);

        // if increase current index
        if (idxDto.getNewIdx() > idxDto.getCurIdx()) {
            myCourseMapper.moveIdxForward(idxDto);
        }

        // if decrease current index
        if (idxDto.getNewIdx() < idxDto.getCurIdx()) {
            myCourseMapper.moveIdxBackward(idxDto);
        }
    }
}
