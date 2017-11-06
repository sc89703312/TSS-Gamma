package com.nju.onlineexam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "exam_question")
@Data
public class ExamQuestionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @ManyToOne
    QuestionEntity question;

    @ManyToOne
    ExamEntity exam;

    int score;

}
