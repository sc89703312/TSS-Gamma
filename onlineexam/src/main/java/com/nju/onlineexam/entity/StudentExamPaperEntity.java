package com.nju.onlineexam.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "student_exam_paper")
@Data
public class StudentExamPaperEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    String selected;

    Boolean isRight;

    @ManyToOne
    QuestionEntity question;

    @ManyToOne
    StudentExamEntity studentExam;

}
