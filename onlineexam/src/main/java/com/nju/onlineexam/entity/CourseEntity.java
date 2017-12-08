package com.nju.onlineexam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "course")
@Data
@ToString(exclude = {"examList","questionList"})
@EqualsAndHashCode(exclude = {"examList","questionList"})
public class CourseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @ManyToOne
    TeacherEntity teacher;

    String name;

    @OneToMany(mappedBy = "course")
    List<ExamEntity> examList;

    @OneToMany(mappedBy = "course")
    List<QuestionEntity> questionList;
}
