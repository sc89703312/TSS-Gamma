package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.StudentExamPaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentExamPaperRepo extends JpaRepository<StudentExamPaperEntity,Integer> {


    @Query("select sep.question.id  from student_exam_paper sep where sep.studentExam.id = ?1 and sep.isRight = 1")
    List<Integer> getSolvedQuestionIdList (int stdExamId);

    List<StudentExamPaperEntity> getByStudentExamId(int studentExamId);

}
