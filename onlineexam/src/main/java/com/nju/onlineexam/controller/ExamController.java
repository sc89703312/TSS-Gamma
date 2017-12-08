package com.nju.onlineexam.controller;

import com.nju.onlineexam.dao.CourseRepo;
import com.nju.onlineexam.dao.ExamRepo;
import com.nju.onlineexam.dao.QuestionRepo;
import com.nju.onlineexam.dao.StudentExamRepo;
import com.nju.onlineexam.entity.CourseEntity;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import com.nju.onlineexam.entity.ExamQuestionEntity;

import com.nju.onlineexam.excel.ExcelHelper;
import com.nju.onlineexam.service.ExamService;
import com.nju.onlineexam.service.StudentExcelReader;
import com.nju.onlineexam.util.FileHelper;
import com.nju.onlineexam.vo.CreateExamVo;
import com.nju.onlineexam.vo.ExamVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class ExamController {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    ExamRepo examRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    StudentExamRepo studentExamRepo;

    @Autowired
    StudentExcelReader studentExcelReader;

    @Autowired
    ExamService examService;


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

    /**
     * 创建考试
     * 这个方法比较慢，可能要几秒
     * @param courseId
     * @param createExamVo
     * @return
     */
    @PostMapping("/course/{courseId}/exam/create")
    @Transactional
    public ExamVo createExam(@PathVariable int courseId,
                           @RequestBody @Valid CreateExamVo createExamVo){

        if( ! courseRepo.existsById(courseId) ){
            throw new RuntimeException("course id not exist");
        }

        //创建考试
        ExamEntity examEntity = new ExamEntity();
        examEntity.setCourse(courseRepo.getOne(courseId));
        examEntity.setName(createExamVo.getName());
        examEntity.setStartTime(createExamVo.getStartTime());
        examEntity.setEndTime(createExamVo.getEndTime());
        examEntity.setPassword(getExamPassword());
        examRepo.save(examEntity);

        //解析excel，创建考生
        List<StudentExamEntity> studentExamList = getStudentList(createExamVo.getStudentsFile());
        studentExamList.forEach(s->s.setExam(examEntity));
        studentExamRepo.saveAll(studentExamList);

        //随机选出考题
        examService.selectExamQuestion(examEntity,createExamVo.getScoreList());

        // TODO: 2017/12/8 初始化student_exam_paper

        return convertToVo(examEntity);
    }

    private String getExamPassword(){
        return "password"+(new Random().nextInt(10000));
    }

    //应该有一个交卷的接口
    //考试中的逻辑可以前段完全控制

    private List<StudentExamEntity> getStudentList(String fileName){
        Path filePath = FileHelper.openFile(fileName);
        try {

            FileInputStream iStream = new FileInputStream(filePath.toFile());
            return studentExcelReader.readExcel(iStream,fileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    private ExamVo convertToVo(ExamEntity examEntity){
        ExamVo examVo = new ExamVo();
        examVo.setId(examEntity.getId());
        examVo.setName(examEntity.getName());
        examVo.setStartTime(examEntity.getStartTime());
        examVo.setEndTime(examEntity.getEndTime());
        examVo.setFinish(false);
        return examVo;
    }


}
