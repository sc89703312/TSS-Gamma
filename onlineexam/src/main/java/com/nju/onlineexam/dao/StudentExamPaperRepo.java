package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.StudentExamPaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentExamPaperRepo extends JpaRepository<StudentExamPaperEntity,Integer> {
}
