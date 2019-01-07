package com.tessco.mass_upload.services;



import com.tessco.mass_upload.entities.MassUploadCSV;
import com.tessco.mass_upload.services.database_services.MassUploadCSVService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CSVFileProcessingService {
    private final static Logger logger = LoggerFactory.getLogger(CSVFileProcessingService.class);

    private final MassUploadCSVService massUploadCSVService;

    @Autowired
    public CSVFileProcessingService(MassUploadCSVService massUploadCSVService)
    {
        this.massUploadCSVService = massUploadCSVService;
    }

    public void processFile(MultipartFile file, StringBuilder stringBuilder) throws RuntimeException
    {
        try
        {
            String fileName = file.getOriginalFilename();

            if(massUploadCSVService.checkFileName(fileName))
            {
                stringBuilder
                        .append(fileName)
                        .append(" has already been uploaded.");
                throw new RuntimeException();
            }

            String content = IOUtils.toString(file.getInputStream(), StandardCharsets.UTF_8);
            List<String> lines = IOUtils.readLines(new StringReader(content.replace(",", "\t")));

            String trimmedContent = trimFileContent(lines);
            lines = IOUtils.readLines(new StringReader(trimmedContent));

            String accountNumber = lines.get(0).trim();
            if(isAccountNumberValid(accountNumber))
            {
                lines.remove(0);
                massUploadCSVService.save(
                        new MassUploadCSV(file.getOriginalFilename(), accountNumber,
                                StringUtils.join(lines, "\n")));
            } else {
                stringBuilder
                        .append(fileName)
                        .append(" does not have a Valid Account Number.").append("\n");
            }
        } catch (IOException e)
        {
            stringBuilder.append(file.getOriginalFilename()).append(" was not uploaded!\n");
            logger.error("Could not parse content from file!");
        }
    }

    private boolean isAccountNumberValid(String accountNumber)
    {
        return StringUtils.isNumeric(accountNumber) && accountNumber.length() == 7;
    }

    private String trimFileContent(List<String> lines)
    {
        StringBuilder stringTrimmer = new StringBuilder();

        for (String line : lines)
        {
            if (!line.trim().equals(""))
            {
                stringTrimmer.append(line).append("\n");
            }
        }
        return stringTrimmer.toString();
    }

}
