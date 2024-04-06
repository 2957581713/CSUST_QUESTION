package com.csust.csustquestion.controller.admin;

import com.csust.csustquestion.domain.Questionnaire;
import com.csust.csustquestion.result.ResultEntity;
import com.csust.csustquestion.service.QuestionnaireService;
import com.csust.csustquestion.vo.QuestionnaireVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionnaire")
public class AdminQuestionnaireController {

    @Resource
    private QuestionnaireService questionnaireService;

    @GetMapping("/getQuestionAndStatus")
    public ResultEntity getQuestionAndStatus(){
        String[] result = questionnaireService.getQuestionAndStatus();
        return ResultEntity.successWithData(result);
    }

//    获得问卷的详情
    @GetMapping("/get")
    public ResultEntity getQuestionnaire(String questionnaireName){
        System.out.println(questionnaireName);
        QuestionnaireVo questionnaireVo = questionnaireService.getQuestionnaire(questionnaireName);
        return ResultEntity.successWithData(questionnaireVo);
    }

    @PostMapping("/create")
    public ResultEntity createQuestionnaire(@RequestBody @Valid QuestionnaireVo questionnaireVo){
        questionnaireService.create(questionnaireVo);
        return ResultEntity.successWithoutData();
    }


}
