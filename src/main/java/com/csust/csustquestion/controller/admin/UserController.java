package com.csust.csustquestion.controller.admin;


import com.csust.csustquestion.result.ResultEntity;
import com.csust.csustquestion.vo.WebUserVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/login")
    public ResultEntity<Object> login(@RequestBody @Valid WebUserVo req){

        return new ResultEntity<>();
    }

}
