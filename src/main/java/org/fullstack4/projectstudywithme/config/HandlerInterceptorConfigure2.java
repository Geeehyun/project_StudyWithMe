package org.fullstack4.projectstudywithme.config;

import org.fullstack4.projectstudywithme.interceptor.AutoLoginInterceptor;
import org.fullstack4.projectstudywithme.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HandlerInterceptorConfigure2 implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;
    @Autowired
    public HandlerInterceptorConfigure2(LoginCheckInterceptor loginCheckInterceptor) {
        super();
        this.loginCheckInterceptor = loginCheckInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/main/*", "/mystudy/*", "/shared/*", "/mypage/*");
    }
}
