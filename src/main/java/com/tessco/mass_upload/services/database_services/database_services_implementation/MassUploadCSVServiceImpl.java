package com.tessco.mass_upload.services.database_services.database_services_implementation;

import com.tessco.mass_upload.entities.MassUploadCSV;
import com.tessco.mass_upload.repositories.MassUploadCSVRepository;
import com.tessco.mass_upload.services.database_services.MassUploadCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MassUploadCSVServiceImpl implements MassUploadCSVService {
    private final MassUploadCSVRepository massUploadCSVRepository;

    @Autowired
    public MassUploadCSVServiceImpl(MassUploadCSVRepository massUploadCSVRepository)
    {
        this.massUploadCSVRepository = massUploadCSVRepository;
    }

    @Override
    public void save(MassUploadCSV massUploadCSV) {
        massUploadCSVRepository.save(massUploadCSV);
    }

    @Override
    public boolean checkFileName(String fileName)
    {
        MassUploadCSV massUploadCSV = massUploadCSVRepository.findByCsvFileName(fileName);
        return massUploadCSV != null;
    }
}
