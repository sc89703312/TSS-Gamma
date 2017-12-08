package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepo extends JpaRepository<QuestionEntity,Integer> {

    @Query("select q.id from question q where q.course.id = ?1")
    List<Integer> findIdsByCourseId(int courseId);

}
