package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.controller;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.DataExport;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service.DataExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data-exports")
public class DataExportController {

    @Autowired
    private DataExportService dataExportService;

    // GET: Retrieve all data exports
    @GetMapping
    public List<DataExport> getAllDataExports() {
        return dataExportService.findAllDataExports();
    }

    // GET: Retrieve a data export by ID
    @GetMapping("/{id}")
    public ResponseEntity<DataExport> getDataExportById(@PathVariable Integer id) {
        return dataExportService.findDataExportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Retrieve data exports by User ID
    @GetMapping("/user/{userId}")
    public List<DataExport> getDataExportsByUserId(@PathVariable Integer userId) {
        return dataExportService.findDataExportsByUserId(userId);
    }

    // POST: Create a new data export
    @PostMapping
    public ResponseEntity<DataExport> createDataExport(@RequestBody DataExport dataExport) {
        DataExport savedDataExport = dataExportService.saveDataExport(dataExport);
        return ResponseEntity.ok(savedDataExport);
    }

    // PUT: Update an existing data export
    @PutMapping("/{id}")
    public ResponseEntity<DataExport> updateDataExport(@PathVariable Integer id, @RequestBody DataExport dataExportDetails) {
        return dataExportService.findDataExportById(id).map(dataExport -> {
            dataExport.setExportType(dataExportDetails.getExportType());
            dataExport.setFilePath(dataExportDetails.getFilePath());
            dataExport.setStatus(dataExportDetails.getStatus());
            DataExport updatedDataExport = dataExportService.saveDataExport(dataExport);
            return ResponseEntity.ok(updatedDataExport);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Remove a data export by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataExport(@PathVariable Integer id) {
        if (dataExportService.findDataExportById(id).isPresent()) {
            dataExportService.deleteDataExportById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

