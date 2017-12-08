package com.nju.onlineexam.controller;

import com.nju.onlineexam.util.DateHelper;
import com.nju.onlineexam.util.FileHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
public class FileController {

    private static final String format = "yyyy_MM_dd_HH'h'mm'm'ss's'";

    @PostMapping("/file/upload")
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam(name="fileName",required = false) String fileName)
            throws IOException {

        if( fileName==null || "".equals(fileName) ){
            fileName = file.getOriginalFilename();
        }

        String serverFileName = DateHelper.dateToString(new Date(),format) + "_" + fileName;
        FileHelper.saveFile(serverFileName,file.getInputStream());
        return serverFileName;
    }

}
