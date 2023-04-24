package com.adm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // 필요한 경우 추가적인 쿼리 메소드를 정의하세요.
}
