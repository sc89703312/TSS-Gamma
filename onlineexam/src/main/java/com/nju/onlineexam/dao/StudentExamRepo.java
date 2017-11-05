package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.StudentExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by sbin on 2017/11/5.
 */
public interface StudentExamRepo extends JpaRepository<StudentExamEntity,Integer> {

    List<StudentExamEntity> findByExamId(int examId);

}
