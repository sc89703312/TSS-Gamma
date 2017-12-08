package com.nju.onlineexam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "student_exam")
@Data
@ToString(exclude = "studentExamPaperList")
@EqualsAndHashCode(exclude = "studentExamPaperList")
public class StudentExamEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    Integer score;

    @ManyToOne
    StudentEntity student;

    @ManyToOne
    ExamEntity exam;

    @OneToMany(mappedBy = "studentExam")
    List<StudentExamPaperEntity> studentExamPaperList;

}
