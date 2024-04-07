package com.csust.csustquestion.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.csust.csustquestion.domain.Questionnaire;
import com.csust.csustquestion.enums.TargetEnum;
import com.csust.csustquestion.result.ResultEntity;
import com.csust.csustquestion.service.*;
import com.csust.csustquestion.util.Base64Utils;
import com.csust.csustquestion.util.WeChatUtil;
import com.csust.csustquestion.vo.QuestionnaireVo;
import com.csust.csustquestion.vo.Vo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
@Tag(name = "微信相关接口")
public class WxController {

    @Resource
    private UserService userService;
    @Resource
    private QuestionnaireService questionnaireService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;

    @PostMapping("/login")
    @Operation(summary = "微信登录接口")
    public ResultEntity login(@RequestBody Vo vo){
        String openId = userService.login(vo);
        return ResultEntity.successWithData(Base64Utils.encode(openId));
    }


    @GetMapping({"/questionnaire/getQuestionAndStatus/ing"})
    @Operation(summary = "查询当前是否有正在进行的问卷")
    public ResultEntity getQuestionAndStatus2() {
        return ResultEntity.successWithData(questionnaireService.getQuestionAndStatus2());
    }

    @GetMapping("/questionnaire/get")
    @Operation(summary = "查出正在进行问卷的具体信息")
    public ResultEntity getQuestionnaire(String questionnaireName) {
        QuestionnaireVo questionnaireVo = questionnaireService.getQuestionnaire(questionnaireName);
        return ResultEntity.successWithData(questionnaireVo);
    }


    @GetMapping({"/questionnaire/boolean"})
    @Operation(summary = "验证用户是否可以填问卷")
    public boolean getAuthority(@RequestParam("questionnaireName") String questionnaireName, @RequestParam("open_id") String open_id, @RequestParam("identity") String identity) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireByName(questionnaireName);
        Long teacherId = null;
        Long studentId = null;
        if(questionnaire.getQuestionnaireTarget().equals(identity)){
            if(identity.equals(TargetEnum.TEACHER.getTarget())) teacherId = teacherService.getTeacherId(Base64Utils.decode(open_id),questionnaire.getId());
            if(identity.equals(TargetEnum.STUDENT.getTarget())) studentId = studentService.getStudentId(Base64Utils.decode(open_id),questionnaire.getId());
            else return false;
        }else return false;
        if (studentId == null && teacherId == null)
            return true;
        return false;
    }



}
