package com.jebytek.server.mapper.customized;

import com.jebytek.server.dto.IdxDto;
import org.apache.ibatis.annotations.Param;

public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);

    int updateIdx(IdxDto idxDto);

    int moveIdxForward(IdxDto idxDto);

    int moveIdxBackward(IdxDto idxDto);
}
