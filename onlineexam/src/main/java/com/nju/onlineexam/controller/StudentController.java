package com.nju.onlineexam.controller;


import com.nju.onlineexam.dao.ExamRepo;
import com.nju.onlineexam.dao.StudentExamRepo;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.entity.StudentEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import com.nju.onlineexam.util.DataConverter;
import com.nju.onlineexam.vo.EnterExamParam;
import com.nju.onlineexam.vo.ExamStudentVo;
import com.nju.onlineexam.vo.ExamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    @Autowired
    StudentExamRepo studentExamRepo;

    @Autowired
    ExamRepo examRepo;

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
            return vo;
        }).collect(Collectors.toList());

    }

}
