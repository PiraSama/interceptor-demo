package com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        logger.info("Request URL: {} - Method: {} - START",
                request.getRequestURI(), request.getMethod());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        // Có thể thêm logic xử lý sau khi controller xử lý xong
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime != null) {
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;

            logger.info("Request URL: {} - Method: {} - Status: {} - Time: {} ms",
                    request.getRequestURI(),
                    request.getMethod(),
                    response.getStatus(),
                    executeTime);
        }

        if (ex != null) {
            logger.error("Request ended with exception: ", ex);
        }
    }
}