package com.nju.onlineexam.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "choice")
@Data
public class ChoiceEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @ManyToOne
    QuestionEntity question;

    String description;
    boolean isRightAnswer;

}
