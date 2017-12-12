package com.nju.onlineexam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "question")
@Data
@ToString(exclude = "choiceList")
@EqualsAndHashCode(exclude = "choiceList")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @ManyToOne
    CourseEntity course;

    String description;

    boolean isMultiAnswer;

    @OneToMany(mappedBy = "question")
    List<ChoiceEntity> choiceList;

}
