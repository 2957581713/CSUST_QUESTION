package com.csust.csustquestion.controller.admin;


import com.csust.csustquestion.result.ResultEntity;
import com.csust.csustquestion.service.WebUserService;
import com.csust.csustquestion.util.JWTUtil;
import com.csust.csustquestion.vo.WebUserVo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private WebUserService webUserService;

    @PostMapping("/login")
    public ResultEntity login(@RequestBody @Valid WebUserVo req){
        if(!webUserService.login(req)){
            return ResultEntity.failed("账号密码错误");
        }
        Map<String,String> tokenMap = new HashMap<>();
        String token = JWTUtil.createToken(req.getUserId(), "admin");
        tokenMap.put("token",token);
        tokenMap.put("tokenHead","Authorization");
        return ResultEntity.successWithData(tokenMap);
    }

}
