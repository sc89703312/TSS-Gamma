package com.nju.onlineexam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "exam")
@Data
@ToString(exclude = "examQuestionList")
@EqualsAndHashCode(exclude = "examQuestionList")
public class ExamEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @OneToMany(mappedBy = "exam")
    List<ExamQuestionEntity> examQuestionList;

    @ManyToOne
    CourseEntity course;

    String name;

}
