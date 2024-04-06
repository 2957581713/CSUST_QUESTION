package com.csust.csustquestion.controller.admin;

import com.csust.csustquestion.domain.Questionnaire;
import com.csust.csustquestion.result.ResultEntity;
import com.csust.csustquestion.service.QuestionnaireService;
import com.csust.csustquestion.vo.QuestionnaireVo;
import com.csust.csustquestion.vo.ResultVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionnaire")
@Tag(name = "管理员接口")
public class AdminQuestionnaireController {

    @Resource
    private QuestionnaireService questionnaireService;

    @GetMapping("/getQuestionAndStatus")
    @Operation(summary = "查出所有未失效的问卷名及对象与状态")
    public ResultEntity getQuestionAndStatus(){
        String[] result = questionnaireService.getQuestionAndStatus();
        return ResultEntity.successWithData(result);
    }

//    获得问卷的详情
    @GetMapping("/get")
    @Operation(summary = "根据问卷名查出问卷(未失效的)")
    public ResultEntity getQuestionnaire(String questionnaireName){
        System.out.println(questionnaireName);
        QuestionnaireVo questionnaireVo = questionnaireService.getQuestionnaire(questionnaireName);
        return ResultEntity.successWithData(questionnaireVo);
    }

    @PostMapping("/create")
    @Operation(summary = "新建问卷")
    public ResultEntity createQuestionnaire(@RequestBody @Valid QuestionnaireVo questionnaireVo){
        questionnaireService.create(questionnaireVo);
        return ResultEntity.successWithoutData();
    }

    @PostMapping("/getQuestionAndStatus/update")
    @Operation(summary = "修改问卷接口(让接口变为失效或发布或结束)")
    public ResultEntity updateStatus(@RequestParam("questionnaireName") String questionnaireName,
                                             @RequestParam("questionnaireStatus") String questionnaireStatus){

        questionnaireService.updateStatus(questionnaireName,questionnaireStatus);
        return ResultEntity.successWithoutData();
    }


    @GetMapping({"/getQuestionAndStatus/ing"})
    @Operation(summary = "查之前先判断是否有问卷正在发布,主要是小程序那里一次只能弹一个问卷提示")
    public ResultEntity getQuestionAndStatus2() {
        return ResultEntity.successWithData(questionnaireService.getQuestionAndStatus2());
    }


    @GetMapping("/result/all")
    public ResultEntity getResult(@RequestParam("questionnaireName") String questionnaireName){
        ResultVo resultVo = questionnaireService.getResult(questionnaireName);
        return ResultEntity.successWithData(resultVo);
    }
}
