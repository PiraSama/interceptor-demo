package com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // Kiểm tra nếu đang truy cập trang public (login, register, static resources)
        if (isPublicPage(uri)) {
            return true;
        }

        // Kiểm tra đã login chưa
        if (session == null || session.getAttribute("user") == null) {
            logger.warn("Unauthorized access attempt to: {}", uri);
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // Kiểm tra quyền truy cập (ví dụ: admin pages)
        if (isAdminPage(uri)) {
            String role = (String) session.getAttribute("userRole");
            if (!"ADMIN".equals(role)) {
                logger.warn("Access denied for user to admin page: {}", uri);
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }

        return true;
    }

    private boolean isPublicPage(String uri) {
        return uri.startsWith("/login") ||
                uri.startsWith("/register") ||
                uri.startsWith("/css/") ||
                uri.startsWith("/js/") ||
                uri.startsWith("/images/") ||
                uri.equals("/");

    }

    private boolean isAdminPage(String uri) {
        return uri.startsWith("/admin");
    }
}