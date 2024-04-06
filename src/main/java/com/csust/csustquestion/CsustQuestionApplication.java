package com.csust.csustquestion;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.csust.csustquestion.mapper")
public class CsustQuestionApplication {


    public static void main(String[] args) {
        SpringApplication.run(CsustQuestionApplication.class,args);
    }
}
