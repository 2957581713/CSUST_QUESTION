package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Questionnaire;

import java.util.List;

public interface QuestionnaireMapper {
    List<Questionnaire> selectNotDelete();

    Questionnaire selectByName(String questionnaireName);

    void insert(Questionnaire questionnaire);
}
