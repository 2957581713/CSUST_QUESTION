package com.csust.csustquestion.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.csust.csustquestion.context.LoginContext;
import com.csust.csustquestion.domain.LoginUser;
import com.csust.csustquestion.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod()))
            return true;
        String authorization = request.getHeader("Authorization");//获得token
        if(StrUtil.isBlank(authorization)){
            LOG.error("token不存在！");
            return false;
        }
        boolean validate = JWTUtil.validate(authorization);
        if(validate){
            JSONObject jsonObject = JWTUtil.getJSONObject(authorization);
            LoginUser loginUser = JSONUtil.toBean(jsonObject, LoginUser.class);
            LoginContext.setUserId(loginUser.getUserId());
            LOG.info("token有效，当前登录用户为：{}",loginUser);
            return true;

        }
        LOG.error("token无效");
        return false;


    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginContext.remove();
    }
}
