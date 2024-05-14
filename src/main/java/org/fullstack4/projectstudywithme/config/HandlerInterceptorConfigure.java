package org.fullstack4.projectstudywithme.config;

import org.fullstack4.projectstudywithme.interceptor.AutoLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HandlerInterceptorConfigure implements WebMvcConfigurer {
    private final AutoLoginInterceptor autoLoginInterceptor;
    @Autowired
    public HandlerInterceptorConfigure(AutoLoginInterceptor autoLoginInterceptor) {
        super();
        this.autoLoginInterceptor = autoLoginInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**");
    }
}
