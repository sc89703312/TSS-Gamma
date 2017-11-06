package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<CourseEntity,Integer> {
}
