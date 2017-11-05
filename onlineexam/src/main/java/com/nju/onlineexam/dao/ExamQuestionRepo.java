package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.ExamQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sbin on 2017/11/5.
 */
public interface ExamQuestionRepo extends JpaRepository<ExamQuestionEntity,Integer> {
}
