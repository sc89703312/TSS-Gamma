package com.nju.onlineexam.dao;

import com.nju.onlineexam.entity.TestTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTableRepo extends JpaRepository<TestTableEntity,Integer> {
}
