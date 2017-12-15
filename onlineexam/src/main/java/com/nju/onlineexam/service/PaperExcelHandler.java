package com.nju.onlineexam.service;

import com.nju.onlineexam.dao.ExamRepo;
import com.nju.onlineexam.dao.StudentExamPaperRepo;
import com.nju.onlineexam.dao.StudentExamRepo;
import com.nju.onlineexam.dao.StudentRepo;
import com.nju.onlineexam.entity.*;
import com.nju.onlineexam.excel.ExcelHelper;
import com.nju.onlineexam.util.DataConverter;
import com.nju.onlineexam.util.FileHelper;
import com.nju.onlineexam.vo.StudentPaperRowVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    StudentExamRepo studentExamRepo;

    @Autowired
    StudentExamPaperRepo studentExamPaperRepo;

    @Autowired
    DataConverter dataConverter;

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
    public String generateStudentPaper(int examId,int studentId) throws IOException {

        if( ! examRepo.existsById(examId) ){
            throw new RuntimeException("exam not exist");
        }

        XSSFWorkbook workbook = new XSSFWorkbook("excels/学生试卷模板.xlsx");
        StudentExamEntity temp = studentExamRepo.findByExamIdAndStudentId(examId,studentId);
        List<StudentExamEntity> studentExamEntities = new ArrayList<>();
        studentExamEntities.add(temp);

        excelHelper.convertToExcel(workbook,1,studentExamEntities,studentExamEntity->{
            List<String> cells = new ArrayList<String>();
            cells.add(studentExamEntity.getStudent().getName());
            cells.add(studentExamEntity.getStudent().getNumber());
            cells.add(String.valueOf(studentExamEntity.getScore()));
            return cells;
        });


        List<StudentPaperRowVo> studentPaperRowVos = dataConverter.convertToStudentPaperRowVo(temp);
        excelHelper.convertToExcel(workbook,3,studentPaperRowVos,rowVo->{
            List<String> cells = new ArrayList<>();
            cells.add(rowVo.getQuestion());
            cells.add(String.valueOf(rowVo.getScore()));
            if(rowVo.isRight()){
                cells.add("是");
            }else{
                cells.add("否");
            }
            cells.add(rowVo.getCorrectAnswer());
            cells.add(rowVo.getSelected());
            cells.addAll(rowVo.getChoicesInOrder());
            return cells;
        });

        String fileName = temp.getExam().getName()+"_"+temp.getStudent().getNumber()+"试卷.xlsx";
        FileHelper.saveDownloadExcel(fileName,workbook);
        return fileName;
    }

}
