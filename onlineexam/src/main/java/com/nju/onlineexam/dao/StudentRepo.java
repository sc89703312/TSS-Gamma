package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.StudentEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepo extends JpaRepository<StudentEntity,Integer>{


    List<StudentEntity> findByEmailAndPassword(String email , String password);

}
