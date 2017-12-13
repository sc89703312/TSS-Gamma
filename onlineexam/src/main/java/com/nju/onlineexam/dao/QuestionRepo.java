package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepo extends JpaRepository<QuestionEntity,Integer> {

    @Query("select q.id from question q where q.course.id = ?1")
    List<Integer> findIdsByCourseId(int courseId);

    @Query("select count(c) from choice c where c.question.id = ?1 and is_right_answer = 1")
    Long countAnswers(int questionId);
}
