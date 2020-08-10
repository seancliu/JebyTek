package com.jebytek.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jebytek.server.domain.Instructor;
import com.jebytek.server.domain.InstructorExample;
import com.jebytek.server.dto.InstructorDto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.mapper.InstructorMapper;
import com.jebytek.server.util.CopyUtil;
import com.jebytek.server.util.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorService {
    @Resource
    private InstructorMapper instructorMapper;

    /*
    * retrieve instructors
    * */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        InstructorExample instructorExample = new InstructorExample();
        List<Instructor> instructorList = instructorMapper.selectByExample(instructorExample);
        PageInfo<Instructor> pageInfo = new PageInfo<>(instructorList);
        pageDto.setTotal(pageInfo.getTotal());
        List<InstructorDto> instructorDtoList = new ArrayList<>();
        for (int i = 0, size = instructorList.size(); i < size; i++) {
            Instructor instructor = instructorList.get(i);
            InstructorDto instructorDto = new InstructorDto();
            BeanUtils.copyProperties(instructor, instructorDto);
            instructorDtoList.add(instructorDto);
        }
        pageDto.setList(instructorList);
    }

    /*
     * save a record
     * when id is empty, insert;
     * when id is not empty, update
     * */
    public void save(InstructorDto instructorDto) {
        Instructor instructor = CopyUtil.copy(instructorDto, Instructor.class);
        if (StringUtils.isEmpty(instructorDto.getId())) {
            this.insert(instructor);
        } else {
            this.update(instructor);
        }
    }

    private void insert(Instructor instructor) {
        instructor.setId(UuidUtil.getShortUuid());
        instructorMapper.insert(instructor);
    }

    private void update(Instructor instructor) {
        instructorMapper.updateByPrimaryKey(instructor);
    }

    public void delete(String id) {
        instructorMapper.deleteByPrimaryKey(id);
    }
}
