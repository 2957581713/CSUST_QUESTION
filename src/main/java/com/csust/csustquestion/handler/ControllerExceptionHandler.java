package com.csust.csustquestion.handler;

import com.csust.csustquestion.exception.BusinessException;
import com.csust.csustquestion.result.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultEntity exceptionHandler(Exception e){
        LOG.error("系统异常:{}",e);
        return ResultEntity.failed("系统异常,请联系管理员");
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResultEntity businessExceptionHandler(BusinessException e){
        LOG.error("业务异常:{}",e.getMessage());
        return ResultEntity.failed(e.getMessage());

    }

}
