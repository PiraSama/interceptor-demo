package com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CombinedInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CombinedInterceptor.class);
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 1. Bắt đầu đếm thời gian
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        logger.info("Request START: {} - {}", request.getMethod(), request.getRequestURI());

        // 2. Kiểm tra authentication
        String uri = request.getRequestURI();

        if (isPublicPage(uri)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            logger.warn("Unauthorized access to: {}", uri);
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (isAdminPage(uri)) {
            String role = (String) session.getAttribute("userRole");
            if (!"ADMIN".equals(role)) {
                logger.warn("Access denied to: {}", uri);
                response.sendRedirect(request.getContextPath() + "/403");
                return false;
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime != null) {
            long executeTime = System.currentTimeMillis() - startTime;
            logger.info("Request END: {} - {} - Status: {} - Time: {} ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    executeTime);
        }
    }

    private boolean isPublicPage(String uri) {
        return uri.startsWith("/login") || uri.startsWith("/register") ||
                uri.startsWith("/css/") || uri.startsWith("/js/") ||
                uri.startsWith("/images/") || uri.equals("/") || uri.equals("/403");
    }

    private boolean isAdminPage(String uri) {
        return uri.startsWith("/admin");
    }
}