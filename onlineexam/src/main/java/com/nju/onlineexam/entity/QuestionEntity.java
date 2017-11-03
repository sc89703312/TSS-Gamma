package com.nju.onlineexam.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "question")
@Data
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @ManyToOne
    CourseEntity course;

    String description;

    boolean isMultiAnswer;

}
