package com.tessco.mass_upload.services.database_services;

import com.tessco.mass_upload.entities.MassUploadCSV;

public interface MassUploadCSVService {
    void save(MassUploadCSV massUploadCSV);

    boolean checkFileName(String fileName);
}
