package com.csust.csustquestion.service;

import com.csust.csustquestion.domain.Questionnaire;
import com.csust.csustquestion.vo.QuestionnaireVo;
import com.csust.csustquestion.vo.ResultVo;

public interface QuestionnaireService {


    /**
     * 返回页表里问卷的名字和状态
     * @return
     */
    String[] getQuestionAndStatus();

    QuestionnaireVo getQuestionnaire(String questionnaireName);

    Questionnaire getQuestionnaireByName(String questionnaireName);
    /*
    保存问卷
     */
    void create(QuestionnaireVo questionnaireVo);

    void updateStatus(String questionnaireName, String questionnaireStatus);

    String[] getQuestionAndStatus2();

    ResultVo getResult(String questionnaireName);

    ResultVo getResultByGrade(String questionnaireName, String grade);

    ResultVo getResultBySex(String questionnaireName, String sex);

    ResultVo getResultByCampus(String questionnaireName, String campus);

    ResultVo getResultByAcademyName(String questionnaireName, String academyName);

    ResultVo getResultBySort(String questionnaireName, String sort);
}
