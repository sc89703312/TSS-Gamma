package com.nju.onlineexam.controller;


import com.nju.onlineexam.dao.*;
import com.nju.onlineexam.entity.*;
import com.nju.onlineexam.service.ExamService;
import com.nju.onlineexam.util.DataConverter;
import com.nju.onlineexam.vo.EnterExamParam;
import com.nju.onlineexam.vo.ExamStudentVo;
import com.nju.onlineexam.vo.ExamVo;
import com.nju.onlineexam.vo.SubmitAnswerParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StudentController {


    @Autowired
    StudentExamRepo studentExamRepo;

    @Autowired
    ExamRepo examRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    StudentExamPaperRepo studentExamPaperRepo;

    @Autowired
    ExamService examService;

    /**
     * 查看自己尚未参加的考试列表
     */
    @GetMapping("/student/{id}/exam")
    public List<ExamVo> getUnattendedExams(@PathVariable int id){
        List<ExamEntity> examEntities = studentExamRepo.findUnattendedExams(id);
        return examEntities.stream().map(entity -> {

            return DataConverter.convertToExamVo(entity);

        }).collect(Collectors.toList());
    }

    /**
     * 提交考试验证码,进入考试
     * @param studentId
     * @param examId
     */
    @PostMapping("/student/{studentId}/exam/{examId}")
    public void enterExam(@PathVariable int studentId,
                          @PathVariable int examId,
                          @RequestBody @Valid EnterExamParam enterExamParam){

        StudentExamEntity studentExam = studentExamRepo.findByExamIdAndStudentId(examId,studentId);
        if(studentExam==null){
            throw new RuntimeException("该学生没有参加该考试");
        }
        if(studentExam.getScore() != null){
            throw new RuntimeException("该学生已经交卷");
        }

        ExamEntity examEntity = examRepo.findById(examId).get();
        if( ! examEntity.getPassword().equals(enterExamParam.getCode())){
            throw new RuntimeException("验证码错误");
        }

        return; //验证成功
    }



    /**
     * 获得某场考试的学生列表
     */
    @GetMapping("/exam/{examId}/students")
    @Transactional
    public List<ExamStudentVo> getExamStudent(@PathVariable int examId){

        List<StudentExamEntity> studentExams = studentExamRepo.findByExamId(examId);

        return studentExams.stream().map(studentExam -> {
            StudentEntity studentEntity = studentExam.getStudent();
            ExamStudentVo vo = new ExamStudentVo();
            BeanUtils.copyProperties(studentEntity,vo);
            vo.setScore(studentExam.getScore());
            return vo;
        }).collect(Collectors.toList());

    }

    @PostMapping("/student/{studentId}/exam/{examId}/submit")
    public void submitAnswer(@PathVariable int studentId , @PathVariable int examId , @RequestBody SubmitAnswerParam submitAnswerParam){

        StudentExamEntity studentExamEntity = studentExamRepo.findByExamIdAndStudentId(examId,studentId);
        if(studentExamEntity == null){
            throw new RuntimeException("student_exam not found , studentId :"+studentId + ",examId:"+examId);
        }

        if(studentExamEntity.getScore() != null){
            throw new RuntimeException("该学生已经参加过该考试");
        }

        for( Map.Entry<Integer, int []> entry : submitAnswerParam.getAnswer().entrySet()){
            StudentExamPaperEntity studentExamPaperEntity = new StudentExamPaperEntity();
            studentExamPaperEntity.setStudentExam(studentExamEntity);

            QuestionEntity questionEntity = questionRepo.getOne(entry.getKey());
            if(questionEntity == null){
                throw new RuntimeException("question not found, id:"+entry.getKey());
            }
            studentExamPaperEntity.setQuestion(questionEntity);
            studentExamPaperEntity.setSelected(getStringWithSplitChar(entry.getValue()));
            studentExamPaperEntity.setIsRight( examService.judgeAnswer(entry.getKey(),entry.getValue()));

            studentExamPaperRepo.save(studentExamPaperEntity);
        }

        int socre = examService.calculateSocre(studentId,examId);
        studentExamEntity.setScore(socre);
        studentExamRepo.save(studentExamEntity);

    }


    public String getStringWithSplitChar(int [] array){
        if(array == null || array.length ==0){
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < array.length; i++){
            stringBuilder.append(array[i]).append(",");
        }

        if(stringBuilder.length() > 1){
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        return  stringBuilder.toString();
    }


}
