package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    List<Long> getStudentIdByGradeAndQuestionnaireId(@Param("grade") String grade, @Param("questionnaireId") Long questionnaireId);

    List<Long> getStudentIdBySexAndQuestionnaireId(@Param("sex") String sex,@Param("questionnaireId") Long questionnaireId);

    List<Long> getStudentIdByCampusAndQuestionnaireId(@Param("campus") String campus,@Param("questionnaireId") Long questionnaireId);

    List<Long> getStudentIdByAcademyAdnQuestionnaired(@Param("academy") String academy,@Param("questionnaireId") Long questionnaireId);

    Student getById(Long studentId);
}
