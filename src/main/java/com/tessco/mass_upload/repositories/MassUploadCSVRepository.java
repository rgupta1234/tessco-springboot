package com.tessco.mass_upload.repositories;

import com.tessco.mass_upload.entities.MassUploadCSV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassUploadCSVRepository extends JpaRepository<MassUploadCSV, Integer> {

    MassUploadCSV findByCsvFileName(String fileName);
}
