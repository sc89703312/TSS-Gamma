package com.nju.onlineexam.controller;

import com.nju.onlineexam.dao.ChoiceRepo;
import com.nju.onlineexam.dao.CourseRepo;
import com.nju.onlineexam.dao.QuestionRepo;
import com.nju.onlineexam.entity.ChoiceEntity;
import com.nju.onlineexam.entity.CourseEntity;
import com.nju.onlineexam.entity.QuestionEntity;
import com.nju.onlineexam.service.QuestionExcelReader;
import com.nju.onlineexam.util.FileHelper;
import com.nju.onlineexam.vo.ChoiceVo;
import com.nju.onlineexam.vo.QuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionExcelReader questionExcelReader;

    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    ChoiceRepo choiceRepo;
    @Autowired
    CourseRepo courseRepo;

    @PostMapping("/course/{courseId}/question/upload")
    @Transactional
    public List<QuestionVo> uploadExcel(@PathVariable int courseId, @RequestParam("fileName") String fileName)
            throws IOException {

        if( ! courseRepo.existsById(courseId) ){
            throw new RuntimeException("courseId not exist:"+courseId);
        }

        Path filePath = FileHelper.openFile(fileName);
        FileInputStream iStream = new FileInputStream(filePath.toFile());
        List<QuestionVo> questionVos = questionExcelReader.readExcel(iStream,fileName);

        CourseEntity courseEntity = courseRepo.getOne(courseId);

        for( QuestionVo questionVo : questionVos ){
            saveOneQuestion(questionVo,courseEntity);
        }

        return questionVos;

    }

    private QuestionVo saveOneQuestion(QuestionVo questionVo, CourseEntity courseEntity){

        QuestionEntity questionEntity = new QuestionEntity();
        BeanUtils.copyProperties(questionVo,questionEntity);

        questionEntity.setCourse(courseEntity);
        questionRepo.save(questionEntity);

        questionVo.setId(questionEntity.getId());

        for(ChoiceVo choiceVo : questionVo.getChoices() ){
            ChoiceEntity choiceEntity = new ChoiceEntity();
            BeanUtils.copyProperties(choiceVo,choiceEntity);
            choiceEntity.setQuestion(questionEntity);

            choiceRepo.save(choiceEntity);
            choiceVo.setId(choiceEntity.getId());
        }

        return questionVo;
    }

}
