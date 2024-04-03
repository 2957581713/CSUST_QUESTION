package com.csust.csustquestion.aspect;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


/**
 * 打印日志切面等
 */
@Aspect
@Component
public class LogAspect {


    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    @Pointcut("execution(public * com.csust.csustquestion..*Controller.*(..))")
    public void pointCut(){}


    @Before(value = "pointCut()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        LOG.info("请求来源ip:{}",request.getRemoteAddr());
        LOG.info("请求地址：{}",request.getRequestURL());
        Signature signature = joinPoint.getSignature();
        LOG.info("请求类名方法{}.{}",signature.getDeclaringType(),signature.getName());
        Object[] args = joinPoint.getArgs();
        List<Object> objects = new ArrayList<>(args.length);
        for (int i = 0; i < args.length; i++) {
            if(args[i] instanceof ServletRequest
            || args[i] instanceof ServletResponse
            || args[i] instanceof MultipartFile){
                continue;
            }
            objects.add(args[i]);
        }
//        过滤请求参数，敏感信息不泄漏
        String[] excludeProperties = {"userpassword"};
        PropertyPreFilters propertyPreFilters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter ex = propertyPreFilters.addFilter(excludeProperties);
        ex.addExcludes(excludeProperties);
        LOG.info("请求参数：{}", JSONObject.toJSONString(objects,ex));
    }


    @Around(value = "pointCut()")
    public Object doAround(ProceedingJoinPoint px) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = px.proceed();
        PropertyPreFilters propertyPreFilters = new PropertyPreFilters();
        String[] ex = {"userpassword"};
        PropertyPreFilters.MySimplePropertyPreFilter es = propertyPreFilters.addFilter(ex);
        LOG.info("返回结果：{}",JSONObject.toJSONString(proceed,es));
        LOG.info("---  请求结束，用时：{}",System.currentTimeMillis()-start);
        return proceed;
    }
}
