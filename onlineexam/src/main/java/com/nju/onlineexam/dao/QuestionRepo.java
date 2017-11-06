package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<QuestionEntity,Integer> {
}
