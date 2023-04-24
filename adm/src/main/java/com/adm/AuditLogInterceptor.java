package com.adm;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuditLogInterceptor implements HandlerInterceptor {
    @Autowired
    private AuditLogService auditLogService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 로그인된 사용자의 ID를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();

            // 감사 로그를 생성합니다.
            AuditLog auditLog = new AuditLog();
            auditLog.setUserId(userId);
            auditLog.setActionType(ActionType.fromHttpRequest(request));
            auditLog.setTimestamp(LocalDateTime.now());
            auditLog.setIpAddress(request.getRemoteAddr());

            // 감사 로그를 저장합니다.
            auditLogService.save(auditLog);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 기본적으로 true를 반환하여 요청 처리를 계속 진행합니다.
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 필요한 경우, 여기에 추가 작업을 수행하세요.
    }
}