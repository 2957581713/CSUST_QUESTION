package com.csust.csustquestion.interceptor;

import cn.hutool.core.util.RandomUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.logging.Handler;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private final static Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)) return true;
        MDC.put("LOG_ID",System.currentTimeMillis()+""+ RandomUtil.randomString(3));
        return true;
    }


}
