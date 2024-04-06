package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Question;

import java.util.List;

public interface QuestionMapper {
    List<Question> selectBySurveyId(Long surveyId);

    void insertList(List<Question> questionList);
}
