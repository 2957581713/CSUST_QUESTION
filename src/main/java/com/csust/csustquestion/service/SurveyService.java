package com.csust.csustquestion.service;

import com.csust.csustquestion.domain.Survey;

import java.util.List;

public interface SurveyService {
    List<Survey> getByQuestionId(Long questionnaireId);

    List<Long> getIdsByQuestionId(Long questionnaireId);
}
