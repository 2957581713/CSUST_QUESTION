package com.csust.csustquestion.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//保存当前项目线程信息
public class LoginContext {

    private static  ThreadLocal<String> user = new ThreadLocal<>();

    private static final Logger LOG = LoggerFactory.getLogger(LoginContext.class);
    public static void setUserId(String userId){
        user.set(userId);
    }

    public static String getUserId(){
        try {

            return user.get();
        }catch (Exception e){
            LOG.error("获取当前登录用户异常！");
            throw new RuntimeException();
        }
    }

    public static void remove(){
        user.remove();
    }



}
