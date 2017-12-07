package com.nju.onlineexam.controller;

import com.nju.onlineexam.dao.CourseRepo;
import com.nju.onlineexam.dao.ExamRepo;
import com.nju.onlineexam.entity.CourseEntity;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import com.nju.onlineexam.vo.CreateExamVo;
import com.nju.onlineexam.vo.ExamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class ExamController {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    ExamRepo examRepo;

    @GetMapping("/course/{courseId}/exam")
    @Transactional
    public List<ExamVo> getExams(@PathVariable int courseId){
        if( ! courseRepo.existsById(courseId) ){
            throw new RuntimeException("course id not exist");
        }

        CourseEntity courseEntity = courseRepo.getOne(courseId);

        List<ExamVo> examVos = courseEntity.getExamList().stream()
                .map(entity -> {
                    ExamVo examVo = new ExamVo();
                    BeanUtils.copyProperties(entity,examVo);
                    return examVo;
                })
                .collect(Collectors.toList());

        return examVos;
    }

    @GetMapping("/course/{courseId}/exam/create")
    @Transactional
    public ExamVo createExam(@PathVariable int courseId,
                           @RequestBody @Valid CreateExamVo createExamVo){

        if( ! courseRepo.existsById(courseId) ){
            throw new RuntimeException("course id not exist");
        }

        ExamEntity examEntity = new ExamEntity();
        examEntity.setCourse(courseRepo.getOne(courseId));
        examEntity.setName(createExamVo.getName());
        examEntity.setStartTime(createExamVo.getStartTime());
        examEntity.setEndTime(createExamVo.getEndTime());
        examEntity.setPassword(getExamPassword());

        examRepo.save(examEntity);
        return null;
    }

    private String getExamPassword(){
        return "password";
    }

    private List<StudentExamEntity> getStudentList(String fileName){
        return null;
    }

}
