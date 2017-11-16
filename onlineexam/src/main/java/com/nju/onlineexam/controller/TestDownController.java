package com.nju.onlineexam.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("excel")
public class TestDownController {

    @GetMapping("down")
    public ResponseEntity<Resource> downloadExcel(){
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Disposition", "attachment; filename=\"testExcel.xlsx\"");
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(file.length())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(resource);
        return null;
    }

}
