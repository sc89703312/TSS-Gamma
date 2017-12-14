package com.nju.onlineexam.controller;

import com.nju.onlineexam.service.PaperExcelHandler;
import com.nju.onlineexam.vo.FileUrlVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by sbin on 2017/12/7.
 * 考前生成考卷
 * 考后生成考卷
 */
@RestController
public class ExamPaperController {

    @Autowired
    PaperExcelHandler paperExcelHandler;

    @PostMapping("/exam/{examId}/download")
    public FileUrlVo getExamPaper(@PathVariable int examId) throws IOException {
        String filename = paperExcelHandler.generateEmptyPaper(examId);
        return new FileUrlVo("/download/"+filename);
    }

    @PostMapping("/exam/{examId}/papers")
    public FileUrlVo getStudentPaper(@PathVariable int examId,
                                     List<Integer> studentIdList){
        // TODO: 2017/12/14 单个试卷直接返回,多个试卷返回压缩包
        // paperExcelHandler.generateStudentPaper
        return null;
    }

}
