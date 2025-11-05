package com.example.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        // Logic xác thực đơn giản (thực tế nên dùng Spring Security)
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("user", username);
            session.setAttribute("userRole", "ADMIN");
            return "redirect:/dashboard";
        } else if ("user".equals(username) && "user123".equals(password)) {
            session.setAttribute("user", username);
            session.setAttribute("userRole", "USER");
            return "redirect:/dashboard";
        }
        return "redirect:/login?error=true";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/admin/users")
    public String adminUsers() {
        return "admin/users";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}