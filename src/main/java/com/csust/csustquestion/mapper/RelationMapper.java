package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Relation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RelationMapper {
    Integer countByOptionId(Long optionId);

    Integer countByOptionIdAndStudentIdList(@Param("studentIds") List<Long> studentIds,@Param("optionId") Long optionId);

    int countByOptionIdAndTeacherIdList(@Param("subList") List<Long> subList,@Param("optionId") Long optionId);

    List<Relation> getByOptionId(Long optionId);
}
