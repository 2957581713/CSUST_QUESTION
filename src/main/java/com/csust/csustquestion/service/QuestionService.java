package com.csust.csustquestion.service;

import com.csust.csustquestion.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getTypeQuestionInSurveyId(List<Long> surveyIdList,String type);

    List<Question> getBySurveyId(Long surveyId);
}
