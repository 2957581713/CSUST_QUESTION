package com.csust.csustquestion.config;

import com.csust.csustquestion.interceptor.LogInterceptor;
import com.csust.csustquestion.interceptor.LoginInterceptor;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private LogInterceptor logInterceptor;

    private static final Logger LOG = LoggerFactory.getLogger(SpringMvcConfig.class);


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/test/*",
                        "/swagger-ui.html","/v3/api-docs/swagger-config","/v3/api-docs"
                ).order(1);

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**映射webjars资源**/
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }



}
