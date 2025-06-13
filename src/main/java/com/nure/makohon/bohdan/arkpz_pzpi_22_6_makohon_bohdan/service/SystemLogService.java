package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.SystemLog;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemLogService {

    @Autowired
    private SystemLogRepository systemLogRepository;

    public List<SystemLog> findAllSystemLogs() {
        return systemLogRepository.findAll();
    }

    public Optional<SystemLog> findSystemLogById(Long id) {
        return systemLogRepository.findById(id);
    }

    public List<SystemLog> findSystemLogsByType(SystemLog.LogType logType) {
        return systemLogRepository.findByLogType(logType);
    }

    public SystemLog saveSystemLog(SystemLog systemLog) {
        return systemLogRepository.save(systemLog);
    }

    public void deleteSystemLogById(Long id) {
        systemLogRepository.deleteById(id);
    }
}

