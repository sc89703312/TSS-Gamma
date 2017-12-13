package com.nju.onlineexam.controller;


import com.nju.onlineexam.dao.StudentExamRepo;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.vo.ExamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentExamRepo studentExamRepo;

    /**
     * 查看自己尚未参加的考试列表
     */
    @GetMapping("/{id}/exam")
    public List<ExamVo> getUnattendedExams(@PathVariable int id){
        List<ExamEntity> examEntities = studentExamRepo.findUnattendedExams(id);
        return examEntities.stream().map(entity -> {
            ExamVo  examVo = new ExamVo();
            examVo.setId(entity.getId());
            examVo.setName(entity.getName());
            return examVo;
        }).collect(Collectors.toList());
    }


}
