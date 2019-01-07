package com.tessco.mass_upload.controllers;


import com.tessco.mass_upload.services.CSVFileProcessingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {
    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private final CSVFileProcessingService csvFileProcessingService;

    @Autowired
    public FileUploadController(CSVFileProcessingService csvFileProcessingService)
    {
        this.csvFileProcessingService = csvFileProcessingService;
    }

    private boolean hasErrors = false;

    @GetMapping("")
    public String getDefaultForm()
    {
        return "upload";
    }

    @GetMapping("/upload")
    public String getUploadForm()
    {
        return "upload";
    }

    @PostMapping(value = "/upload")
    public String fileUpload(@RequestParam("files") MultipartFile[] files, Model model)
    {
        boolean noErrors = false;
        //Add files that end in .csv to list of files that should processed.
        List<MultipartFile> filesToBeProcessed = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();


        for (MultipartFile file : files)
        {
            String fileName = file.getOriginalFilename();
            if (!StringUtils.contains(fileName, ".csv"))
            {
                hasErrors = true;
                stringBuilder.append(fileName).append(" was not processed. Only .csv files can be processed. \n");
            } else {
                filesToBeProcessed.add(file);
            }
        }

        for (MultipartFile csvFile : filesToBeProcessed)
        {
            try
            {
                csvFileProcessingService.processFile(csvFile, stringBuilder);
            } catch (RuntimeException e) {
                logger.error(e.toString());
                hasErrors = true;
            }
        }

        if(hasErrors)
        {
            model.addAttribute("hasErrors", hasErrors);
            model.addAttribute("filesNotBeingProcessed", stringBuilder.toString());
        } else {
            noErrors = true;
            model.addAttribute("noErrors", noErrors);
            model.addAttribute("happyPath", "All the files have been processed successfully!");
        }

        return "upload";
    }
}
