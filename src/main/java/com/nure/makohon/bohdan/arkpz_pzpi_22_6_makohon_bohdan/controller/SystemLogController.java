package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.controller;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.SystemLog;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-logs")
public class SystemLogController {

    @Autowired
    private SystemLogService systemLogService;

    // GET: Retrieve all system logs
    @GetMapping
    public List<SystemLog> getAllSystemLogs() {
        return systemLogService.findAllSystemLogs();
    }

    // GET: Retrieve a system log by ID
    @GetMapping("/{id}")
    public ResponseEntity<SystemLog> getSystemLogById(@PathVariable Long id) {
        return systemLogService.findSystemLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Retrieve system logs by Log Type
    @GetMapping("/type/{logType}")
    public List<SystemLog> getSystemLogsByType(@PathVariable SystemLog.LogType logType) {
        return systemLogService.findSystemLogsByType(logType);
    }

    // POST: Create a new system log
    @PostMapping
    public ResponseEntity<SystemLog> createSystemLog(@RequestBody SystemLog systemLog) {
        SystemLog savedSystemLog = systemLogService.saveSystemLog(systemLog);
        return ResponseEntity.ok(savedSystemLog);
    }

    // DELETE: Remove a system log by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSystemLog(@PathVariable Long id) {
        if (systemLogService.findSystemLogById(id).isPresent()) {
            systemLogService.deleteSystemLogById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
