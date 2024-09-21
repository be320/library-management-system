package com.system.library.repository;

import com.system.library.model.AuditLog;
import com.system.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
