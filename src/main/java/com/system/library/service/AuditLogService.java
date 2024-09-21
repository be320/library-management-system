package com.system.library.service;

import com.system.library.model.AuditLog;
import com.system.library.model.User;
import com.system.library.repository.AuditLogRepository;
import com.system.library.repository.UserRepository;
import com.system.library.util.enums.EntityEnum;
import com.system.library.util.enums.OperationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditLogService {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditLogRepository auditLogRepository;

    public void auditLog(EntityEnum entityEnum, OperationEnum operationEnum, Long entityId){
        String username =  tokenService.getUsernameFromToken();
        Optional<User> user =  userRepository.findByUsername(username);
        AuditLog auditLog = new AuditLog(entityEnum.name(), entityId, operationEnum.name(), user.get().getId());
        auditLogRepository.save(auditLog);
        auditLog.printDetails();
    }
}
