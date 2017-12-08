package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepo extends JpaRepository<TeacherEntity,Integer> {
    List<TeacherEntity> findByEmailAndPassword(String email , String password);
}
