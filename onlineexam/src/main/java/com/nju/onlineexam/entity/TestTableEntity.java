package com.nju.onlineexam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "testTable")
@Data
public class TestTableEntity {

    @Id
    private int test;

}
