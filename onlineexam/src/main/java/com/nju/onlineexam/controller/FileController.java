package com.nju.onlineexam.controller;

import com.nju.onlineexam.util.DateHelper;
import com.nju.onlineexam.util.FileHelper;
import com.nju.onlineexam.vo.UploadFileResp;
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
    public UploadFileResp upload(@RequestParam("file") MultipartFile file)
            throws IOException {

        String fileName = file.getOriginalFilename();

        String serverFileName = DateHelper.dateToString(new Date(),format) + "_" + fileName;
        FileHelper.saveFile(serverFileName,file.getInputStream());
        return new UploadFileResp(serverFileName);
    }

}
