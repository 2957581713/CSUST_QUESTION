package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    List<Long> getTeacherIdsBySortAndQuestionnaireId(@Param("sort") String sort,
                                                     @Param("questionnaireId") Long questionnaireId);

    List<Long> getTeacherIdsBySexAndQuestionnaireId(@Param("sex") String sex,
                                                    @Param("questionnaireId")Long questionnaireId);

    List<Long> getTeacherIdsByCampusAndQuestionnaireId(@Param("campus") String campus,
                                                       @Param("questionnaireId")Long questionnaireId);

    Teacher getById(Long id);

    Long getIdByOpenId(@Param("openId") String openId,@Param("questionnaireId") Long questionnaireId);

    void insert(Teacher teacher);
}
