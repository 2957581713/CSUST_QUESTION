package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Survey;

import java.util.List;

public interface SurveyMapper {
    List<Survey> getByQuestionId(Long questionnaireId);

    void insertList(List<Survey> surveyList);
}
