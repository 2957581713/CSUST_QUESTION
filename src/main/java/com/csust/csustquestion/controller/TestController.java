package com.csust.csustquestion.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "测试类")
public class TestController {


    @GetMapping("/hello")
    @Operation(summary = "测试方法")
    public String test(){
        return "hello csust";
    }
}
