package com.csust.csustquestion.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RelationMapper {
    Integer countByOptionId(Long optionId);

    Integer countByOptionIdAndStudentIdList(@Param("studentIds") List<Long> studentIds,@Param("optionId") Long optionId);

    int countByOptionIdAndTeacherIdList(@Param("subList") List<Long> subList,@Param("optionId") Long optionId);
}
