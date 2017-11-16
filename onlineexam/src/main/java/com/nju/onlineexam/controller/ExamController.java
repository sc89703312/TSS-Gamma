package com.nju.onlineexam.controller;

import com.nju.onlineexam.dao.CourseRepo;
import com.nju.onlineexam.dao.ExamRepo;
import com.nju.onlineexam.dao.QuestionRepo;
import com.nju.onlineexam.entity.CourseEntity;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.entity.ExamQuestionEntity;
import com.nju.onlineexam.vo.CreateExamVo;
import com.nju.onlineexam.vo.ExamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExamController {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    ExamRepo examRepo;

    @Autowired
    QuestionRepo questionRepo;


    @GetMapping("/course/{courseId}/exam")
    @Transactional
    public List<ExamVo> getExams(@PathVariable int courseId){
        if( ! courseRepo.existsById(courseId) ){
            throw new RuntimeException("course id not exist");
        }

        System.out.println("date.toString:"+(new Date(System.currentTimeMillis()).toString()));

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

    @PostMapping("/course/{courseId}/exam/create")
    @Transactional
    public void createExam(@PathVariable int courseId, @Valid @RequestBody CreateExamVo createExamVo){

//        ExamEntity examEntity

    }

    private List<ExamQuestionEntity> getExamQuestion(List<CreateExamVo.Question> questionVos, int courseId,ExamEntity examEntity){

        List<Integer> questionIds = questionRepo.findIdsByCourseId(courseId);
        //todo 随机选择question
        //new ExamQuentionEntity
        //set Exam和Question
        return null;
    }

}
