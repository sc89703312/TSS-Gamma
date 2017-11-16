package com.nju.onlineexam.controller;

import com.nju.onlineexam.util.FileHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    @PostMapping("/file/upload")
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam("fileName") String fileName) throws IOException {

        String serverFileName = System.currentTimeMillis() + fileName;
        FileHelper.saveFile(fileName,file.getInputStream());
        return serverFileName;
    }

}
