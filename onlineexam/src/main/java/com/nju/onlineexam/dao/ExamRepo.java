package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sbin on 2017/11/5.
 */
public interface ExamRepo extends JpaRepository<ExamEntity,Integer> {
}
