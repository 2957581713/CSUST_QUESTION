package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Questionnaire;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionnaireMapper {
    List<Questionnaire> selectNotDelete();

    Questionnaire selectByName(String questionnaireName);

    void insert(Questionnaire questionnaire);

    void setLase(@Param("questionnaireName") String questionnaireName,@Param("questionnaireStatus") String questionnaireStatus);

    List<Questionnaire> getQuestionNameByStatus(String status);
}
