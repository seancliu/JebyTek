package com.jebytek.server.mapper;

import com.jebytek.server.domain.Instructor;
import com.jebytek.server.domain.InstructorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstructorMapper {
    long countByExample(InstructorExample example);

    int deleteByExample(InstructorExample example);

    int deleteByPrimaryKey(String id);

    int insert(Instructor record);

    int insertSelective(Instructor record);

    List<Instructor> selectByExample(InstructorExample example);

    Instructor selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Instructor record, @Param("example") InstructorExample example);

    int updateByExample(@Param("record") Instructor record, @Param("example") InstructorExample example);

    int updateByPrimaryKeySelective(Instructor record);

    int updateByPrimaryKey(Instructor record);
}