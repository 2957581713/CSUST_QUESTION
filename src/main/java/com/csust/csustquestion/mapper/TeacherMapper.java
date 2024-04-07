package com.csust.csustquestion.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    List<Long> getTeacherIdsBySortAndQuestionnaireId(@Param("sort") String sort,
                                                     @Param("questionnaireId") Long questionnaireId);

    List<Long> getTeacherIdsBySexAndQuestionnaireId(@Param("sex") String sex,
                                                    @Param("questionnaireId")Long questionnaireId);

    List<Long> getTeacherIdsByCampusAndQuestionnaireId(@Param("campus") String campus,
                                                       @Param("questionnaireId")Long questionnaireId);
}
