package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepo extends JpaRepository<QuestionEntity,Integer> {

    @Query("select q.id from question q where q.course.id = ?1")
    List<Integer> findIdsByCourseId(int courseId);

    @Query("select count(c) from choice c where c.question.id = ?1 and c.isRightAnswer = 1")
    Long countAnswers(int questionId);

    int countByCourseId(int courseId);

    @Query("select c.id from choice c where c.question.id = ?1 and c.isRightAnswer = 1")
    List<Integer> findRightChoiceIds(int questionId );

}
