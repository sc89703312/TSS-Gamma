package com.nju.onlineexam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.sql.Date;
import java.util.List;

@Data
public class CreateExamVo {

    @NotBlank
    String name;

    List<Question> questions;

    @NotBlank
    String studentExcel;

    @NotBlank @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    Date startTime;

    @NotBlank @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    Date endTime;

    @Data
    public static class Question{
        @NotBlank
        int score;
    }

}
