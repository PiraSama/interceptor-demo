package com.example.config;

import com.example.interceptor.AuthInterceptor;
import com.example.interceptor.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Đăng ký LoggingInterceptor cho tất cả requests
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/**");

        // Đăng ký AuthInterceptor, loại trừ các trang public
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register",
                        "/css/**", "/js/**", "/images/**");
    }
}
