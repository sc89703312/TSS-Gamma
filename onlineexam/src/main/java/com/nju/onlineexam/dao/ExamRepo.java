package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sbin on 2017/11/5.
 */
public interface ExamRepo extends JpaRepository<ExamEntity,Integer> {
    @Query("select e from exam e where e.startTime > ?1 and e.startTime <= ?2")
    List<ExamEntity> findExamsStartBetween(String start, String end);

    @Query("select s.email from student_exam se , student s where se.exam.id = ?1 and se.student.id = s.id")
    List<String> findStdEmailsByExamId(int id);

    @Query("select eq.question.id from exam_question eq where eq.exam.id = ?1 ")
    List<Integer> findQuestionIdListByExamId(int id);
}
