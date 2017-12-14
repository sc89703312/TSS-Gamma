package com.nju.onlineexam.service;

import com.nju.onlineexam.dao.ExamQuestionRepo;
import com.nju.onlineexam.dao.QuestionRepo;
import com.nju.onlineexam.dao.StudentExamPaperRepo;
import com.nju.onlineexam.dao.StudentExamRepo;
import com.nju.onlineexam.entity.*;
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
    StudentExamRepo studentExamRepo;

    @Autowired
    StudentExamPaperRepo studentExamPaperRepo;

    @Autowired
    ExamQuestionRepo examQuestionRepo;

    @Autowired
    QuestionRepo questionRepo;

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


    public int calculateSocre(int studentId , int examId){

        StudentExamEntity studentExamEntity = studentExamRepo.findByExamIdAndStudentId(examId,studentId);
        List<Integer> questionIds = studentExamPaperRepo.getSolvedQuestionIdList(studentExamEntity.getId());
        return examQuestionRepo.calculateScore(examId,questionIds);
    }

    public boolean judgeAnswer(int questionId ,  int[] selected ){

        List<Integer> rightAnswers = questionRepo.findRightChoiceIds(questionId);

        if(rightAnswers == null || rightAnswers.size() == 0 ){
            throw new RuntimeException("question:"+questionId +",未设置正确答案");
        }

        if(selected == null || selected.length == 0){
            return false;
        }

        if(selected.length != rightAnswers.size()){
            return false;
        }else {
            for( int i = 0; i < selected.length ; i++){
                if(!rightAnswers.contains(selected[i])){
                    return false;
                }
            }
        }
        return true;
    }
}
