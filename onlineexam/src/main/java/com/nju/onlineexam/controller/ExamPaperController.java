package com.nju.onlineexam.controller;

import com.nju.onlineexam.dao.ExamRepo;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.service.PaperExcelHandler;
import com.nju.onlineexam.util.FileHelper;
import com.nju.onlineexam.vo.FileUrlVo;
import com.nju.onlineexam.vo.StudentPaperParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    ExamRepo examRepo;

    @PostMapping("/exam/{examId}/download")
    public FileUrlVo getExamPaper(@PathVariable int examId) throws IOException {
        String filename = paperExcelHandler.generateEmptyPaper(examId);
        return new FileUrlVo("/download/"+filename);
    }

    @PostMapping("/exam/{examId}/papers")
    public FileUrlVo getStudentPaper(@PathVariable int examId,@RequestBody StudentPaperParam studentPaperParam) throws IOException {
        // 单个试卷直接返回,多个试卷返回压缩包
        // paperExcelHandler.generateStudentPaper

        List<Integer> studentIdList = studentPaperParam.getStudentIdList();

        if(studentIdList == null || studentIdList.isEmpty()){
            return null;
        }

        if(studentIdList.size() == 1){
            String fileName = paperExcelHandler.generateStudentPaper(examId,studentIdList.get(0));
            return new FileUrlVo("/download/" +fileName);
        }

        ExamEntity examEntity = this.examRepo.getOne(examId);
        List<String> fileNameList = new ArrayList<>();
        for(Integer studentId : studentIdList){
            String fileName = paperExcelHandler.generateStudentPaper(examId,studentId);
            fileNameList.add(fileName);
        }

        String compressFileName = "学生"+examEntity.getName()+"考试试卷.zip";
        FileHelper.compressExcels(compressFileName,fileNameList);

        return new FileUrlVo("/download/" + compressFileName);
    }

}
