package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sbin on 2017/11/5.
 */
public interface StudentExamRepo extends JpaRepository<StudentExamEntity,Integer> {

    List<StudentExamEntity> findByExamId(int examId);

    @Query("select e from student_exam se , exam e where se.s = ?1 and score is null and e.id = se.exam_id ")
    List<ExamEntity> findUnattendedExams(int stdId);
}
