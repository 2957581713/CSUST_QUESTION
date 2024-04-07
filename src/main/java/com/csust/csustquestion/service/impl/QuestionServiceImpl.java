package com.csust.csustquestion.service.impl;

import com.csust.csustquestion.domain.Question;
import com.csust.csustquestion.enums.QuestionTypeEnum;
import com.csust.csustquestion.mapper.QuestionMapper;
import com.csust.csustquestion.service.QuestionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;
    @Override
    public List<Question> getTypeQuestionInSurveyId(List<Long> surveyIdList,String type) {
        return questionMapper.getTypeQuestionInSurveyId(surveyIdList, type);
    }
}
