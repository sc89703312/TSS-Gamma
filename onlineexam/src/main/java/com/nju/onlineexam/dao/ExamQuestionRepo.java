package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.ExamQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sbin on 2017/11/5.
 */
public interface ExamQuestionRepo extends JpaRepository<ExamQuestionEntity,Integer> {

    @Query("select  sum(eq.score) from exam_question eq where eq.exam.id = ?1 and eq.question.id in ?2")
    Integer calculateScore(int examId , List<Integer> questionId);

}
