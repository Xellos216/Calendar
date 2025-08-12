package com.example.calendar.auth;

import com.example.calendar.controller.AuthController;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        boolean isPost = "POST".equalsIgnoreCase(req.getMethod());

        boolean publicPath =
                path.equals("/") ||
                        path.startsWith("/login") ||
                        path.startsWith("/signup") ||
                        path.startsWith("/static/") ||
                        path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/") ||
                        path.startsWith("/favicon") ||
                        path.startsWith("/h2-console") ||
                        path.startsWith("/api/auth/login") ||
                        path.startsWith("/api/auth/logout") ||
                        (path.startsWith("/api/users") && isPost);

        if (publicPath) {
            chain.doFilter(request, response);
            return;
        }

        var session = req.getSession(false);
        if (session == null || session.getAttribute(AuthController.SESSION_KEY) == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write("{\"message\":\"로그인이 필요합니다.\"}");
            return;
        }
        chain.doFilter(request, response);
    }


}
