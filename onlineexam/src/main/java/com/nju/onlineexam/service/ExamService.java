package com.nju.onlineexam.service;

import com.nju.onlineexam.dao.ExamQuestionRepo;
import com.nju.onlineexam.entity.CourseEntity;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.entity.ExamQuestionEntity;
import com.nju.onlineexam.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * Created by sbin on 2017/12/8.
 */
@Component
public class ExamService {

    @Autowired
    ExamQuestionRepo examQuestionRepo;

    /**
     * 随机选择试题，设置对应分数
     */
    @Transactional
    public void selectExamQuestion(ExamEntity examEntity, List<Integer> scoreList){
        CourseEntity courseEntity = examEntity.getCourse();
        List<QuestionEntity> questions =courseEntity.getQuestionList();

        Random random = new Random();
        for(int i : scoreList){
            if(questions.size() == 0){
                break;
            }

            QuestionEntity questionEntity = questions
                    .remove( random.nextInt(questions.size()) );

            ExamQuestionEntity examQuestion = new ExamQuestionEntity();
            examQuestion.setExam(examEntity);
            examQuestion.setQuestion(questionEntity);
            examQuestion.setScore(i);

            examQuestionRepo.save(examQuestion);
        }
    }

}
