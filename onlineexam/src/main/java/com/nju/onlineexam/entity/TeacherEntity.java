package com.nju.onlineexam.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name="teacher")
@Data
//这里双向引用,不exclude会递归
@ToString(exclude = "courseList")
@EqualsAndHashCode(exclude = "courseList")
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    String name;
    String email;
    String password;

    @OneToMany(mappedBy = "teacher")
    List<CourseEntity> courseList;
}
