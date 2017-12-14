package com.nju.onlineexam.service;

import com.nju.onlineexam.dao.ExamRepo;
import com.nju.onlineexam.dao.StudentRepo;
import com.nju.onlineexam.entity.ChoiceEntity;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.entity.ExamQuestionEntity;
import com.nju.onlineexam.entity.QuestionEntity;
import com.nju.onlineexam.excel.ExcelHelper;
import com.nju.onlineexam.util.FileHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbin on 2017/12/14.
 */
@Component
public class PaperExcelHandler {

    @Autowired
    ExamRepo examRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    ExcelHelper excelHelper;

    /**
     * 生成考前试卷excel,返回文件名
     * @param examId
     * @return
     */
    @Transactional
    public String generateEmptyPaper(int examId) throws IOException {

        if( ! examRepo.existsById(examId) ){
            throw new RuntimeException("exam not exist");
        }

        XSSFWorkbook workbook = new XSSFWorkbook("excels/考前试卷模板.xlsx");

        ExamEntity examEntity = examRepo.findById(examId).get();
        List<ExamQuestionEntity> questionEntities = examEntity.getExamQuestionList();
        excelHelper.convertToExcel(workbook,1,questionEntities,questionEntity->{

            List<String> cells = new ArrayList<String>();
            QuestionEntity question = questionEntity.getQuestion();
            cells.add(question.getDescription());
            cells.add(questionEntity.getScore()+"");

            if(question.isMultiAnswer()){
                cells.add("是");
            }else{
                cells.add("否");
            }

            for(ChoiceEntity choice : question.getChoiceList()){
                cells.add(choice.getDescription());
            }
            return cells;
        });

        String fileName = examEntity.getName()+"试卷.xlsx";
        FileHelper.saveDownloadExcel(fileName,workbook);
        return fileName;
    }

    @Transactional
    public String generateStudentPaper(int examId,int studentId){
        return null;
    }

}
