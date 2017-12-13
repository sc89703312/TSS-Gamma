package com.nju.onlineexam.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExamInfoVo {

    List<Integer>  questionIdList;

    public ExamInfoVo(){
        this.questionIdList = new ArrayList<>();
    }
}
