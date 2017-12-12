package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeacherRepo extends JpaRepository<TeacherEntity,Integer> {
    TeacherEntity findByEmailAndPassword(String email , String password);

    TeacherEntity findByEmail(String email);
}
