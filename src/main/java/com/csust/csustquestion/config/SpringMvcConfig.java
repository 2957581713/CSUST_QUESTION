package com.csust.csustquestion.config;

import com.csust.csustquestion.interceptor.LogInterceptor;
import com.csust.csustquestion.interceptor.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private LogInterceptor logInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login").order(1);

        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**").order(0);
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(new String[] { "*" }).
                allowedMethods(new String[] { "*" }).
                allowCredentials(true)
                .maxAge(3600L)
                .allowedHeaders(new String[] { "*" });
    }
}
