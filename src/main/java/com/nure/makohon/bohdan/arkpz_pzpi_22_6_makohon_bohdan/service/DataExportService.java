package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.DataExport;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.DataExportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataExportService {

    @Autowired
    private DataExportRepository dataExportRepository;

    public List<DataExport> findAllDataExports() {
        return dataExportRepository.findAll();
    }

    public Optional<DataExport> findDataExportById(Integer id) {
        return dataExportRepository.findById(id);
    }

    public List<DataExport> findDataExportsByUserId(Integer userId) {
        return dataExportRepository.findByUserUserId(userId);
    }

    public DataExport saveDataExport(DataExport dataExport) {
        return dataExportRepository.save(dataExport);
    }

    public void deleteDataExportById(Integer id) {
        dataExportRepository.deleteById(id);
    }
}

