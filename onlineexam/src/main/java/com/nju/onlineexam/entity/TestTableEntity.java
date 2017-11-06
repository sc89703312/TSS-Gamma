package com.nju.onlineexam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "test_table")
@Data
public class TestTableEntity {

    @Id
    private int test;

    String name;

}
