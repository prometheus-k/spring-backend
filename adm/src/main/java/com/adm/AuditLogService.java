package com.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public AuditLog save(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    // 필요한 경우, 조회 기능을 추가하세요.
}
