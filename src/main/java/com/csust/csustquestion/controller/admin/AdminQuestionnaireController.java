package com.csust.csustquestion.controller.admin;

import com.csust.csustquestion.result.ResultEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questionnaire")
public class AdminQuestionnaireController {


    @GetMapping("/getQuestionAndStatus")
    public ResultEntity getQuestionAndStatus(){

        return null;
    }

}
