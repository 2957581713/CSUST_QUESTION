package com.csust.csustquestion.service;

import com.csust.csustquestion.vo.QuestionnaireVo;
import com.csust.csustquestion.vo.ResultVo;

public interface QuestionnaireService {


    /**
     * 返回页表里问卷的名字和状态
     * @return
     */
    String[] getQuestionAndStatus();

    QuestionnaireVo getQuestionnaire(String questionnaireName);

    /*
    保存问卷
     */
    void create(QuestionnaireVo questionnaireVo);

    void updateStatus(String questionnaireName, String questionnaireStatus);

    String[] getQuestionAndStatus2();

    ResultVo getResult(String questionnaireName);
}
