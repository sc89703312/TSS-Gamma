package com.nju.onlineexam.entity;

import com.nju.onlineexam.util.DateHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
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

    //这是sqlite-jdbc的一个bug,不能正确转化日期,因此底层用string表示
    String startTime;
    String endTime;

    String password;

    public String getStartTimeInString(){
        return this.startTime;
    }

    public Date getStartTime() {
        return DateHelper.stringToDate(startTime);
    }

    public void setStartTime(Date startTime) {
        this.startTime = DateHelper.dateToString(startTime);
    }

    public Date getEndTime() {
        return DateHelper.stringToDate(endTime);
    }

    public void setEndTime(Date endTime) {
        this.endTime = DateHelper.dateToString(endTime);
    }
}
