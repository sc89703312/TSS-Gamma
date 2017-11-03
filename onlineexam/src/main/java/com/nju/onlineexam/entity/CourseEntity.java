package com.nju.onlineexam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "course")
@Data
@ToString(exclude = "examList")
@EqualsAndHashCode(exclude = "examList")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @ManyToOne
    TeacherEntity teacher;

    String name;

    @OneToMany(mappedBy = "course")
    List<ExamEntity> examList;

}
