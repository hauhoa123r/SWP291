package org.project.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger("REQUEST_LOG");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.matches(".*\\.(css|js|png|jpg|jpeg|gif|ico|woff2)$") || uri.equals("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        try {
            filterChain.doFilter(wrappedRequest, response);
        } finally {
            logUserAction(wrappedRequest);
        }
    }

    private void logUserAction(ContentCachingRequestWrapper request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String user = getCurrentUser();
        String roles = getCurrentUserRoles();
        String body = "";


        if (method.matches("(?i)GET|POST|PUT|PATCH|DELETE")) {
            body = getRequestBody(request);
            if (body.length() > 500) {
                body = body.substring(0, 500) + "...";
            }
        }

        logger.info("📌 [{}] {} {} từ IP: {} bởi người dùng: {} (vai trò: {}){}",
                LocalDateTime.now(), method, uri, ip, user, roles,
                (body.isBlank() ? "" : "\nNội dung gửi: " + body));
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        try {
            String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

            body = body.replaceAll("(?i)(\"password\"\\s*:\\s*\")(.*?)(\")", "$1*****$3");
            body = body.replaceAll("(?i)(password=)([^&]*)", "$1*****");

            body = body.replaceAll("(?i)(\"token\"\\s*:\\s*\")(.*?)(\")", "$1[HIDDEN]$3");
            body = body.replaceAll("(?i)(token=)([^&]*)", "$1[HIDDEN]");

            return body;
        } catch (Exception e) {
            return "[KHÔNG ĐỌC ĐƯỢC NỘI DUNG]";
        }
    }

    private String getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();

                if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User oauth2User) {
                    String email = oauth2User.getAttribute("email");
                    return email != null ? email : "[Không có email từ OAuth2]";
                }
                return authentication.getName();
            }
        } catch (Exception e) {
            return "người dùng chưa đăng nhập";
        }
        return "người dùng chưa đăng nhập";
    }


    private String getCurrentUserRoles() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return "Không xác định";
            }
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
        } catch (Exception e) {
            return "Không xác định";
        }
    }
}
