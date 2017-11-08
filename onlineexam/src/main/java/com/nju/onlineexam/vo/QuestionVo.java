package com.nju.onlineexam.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVo {

    int id;
    String description;
    boolean isMultiAnswer;

    List<ChoiceVo> choices;

}
