package com.nju.onlineexam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="student")
@Data
public class StudentEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    String name;
    String number;
    String email;
    String password;

}
