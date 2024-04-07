package com.csust.csustquestion.service.impl;

import com.csust.csustquestion.domain.Survey;
import com.csust.csustquestion.mapper.SurveyMapper;
import com.csust.csustquestion.service.SurveyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Resource
    private SurveyMapper surveyMapper;


    @Override
    public List<Survey> getByQuestionId(Long questionnaireId) {
        return surveyMapper.getByQuestionId(questionnaireId);
    }

    @Override
    public List<Long> getIdsByQuestionId(Long questionnaireId) {
        return surveyMapper.getIdsByQuestionId(questionnaireId);
    }
}
