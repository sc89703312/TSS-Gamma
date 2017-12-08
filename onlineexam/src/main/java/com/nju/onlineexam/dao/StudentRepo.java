package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.StudentEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<StudentEntity,Integer>{

    StudentEntity findByEmail(String email);

}
