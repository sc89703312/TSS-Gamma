package com.nju.onlineexam.controller;

import com.nju.onlineexam.util.FileHelper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class DownloadController {

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadExcel(@PathVariable String filename) throws FileNotFoundException {

        System.out.println("download:"+filename);
        File file = FileHelper.openDownloadFile(filename).toFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        String quoteFileName = "\"" + filename + "\"";
        headers.set("Content-Disposition", "attachment; filename="+quoteFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
