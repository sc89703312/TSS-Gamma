package com.nju.onlineexam.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static com.nju.onlineexam.controller.Const.SUCC_RET;

@RestController("question")
public class QuestionController {

    @PostMapping("uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file){

        return SUCC_RET;
    }

}
