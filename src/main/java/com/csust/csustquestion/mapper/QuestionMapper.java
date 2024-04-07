package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {
    List<Question> selectBySurveyId(Long surveyId);

    void insertList(List<Question> questionList);

    List<Question> getTypeQuestionInSurveyId(@Param("surveyIdList") List<Long> surveyIdList,@Param("type") String type);
}
