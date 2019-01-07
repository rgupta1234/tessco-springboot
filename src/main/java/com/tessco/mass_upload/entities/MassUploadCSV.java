package com.tessco.mass_upload.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@SequenceGenerator(name = "massuploadcsvseq", sequenceName = "massupload_csv_id_seq", allocationSize = 0)
@Table(name = "massupload_csv")
public class MassUploadCSV {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "massuploadcsvseq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "csv_file_name")
    private String csvFileName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "csv_data")
    private String csvData;

    @Column(name = "processed_by_backend")
    private boolean processedByBackEnd;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

    @Column(name = "last_modified_date")
    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    public MassUploadCSV() {
    }

    public MassUploadCSV(String csvFileName, String accountNumber, String csvData) {
        this.csvFileName = csvFileName;
        this.accountNumber = accountNumber;
        this.csvData = csvData;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
