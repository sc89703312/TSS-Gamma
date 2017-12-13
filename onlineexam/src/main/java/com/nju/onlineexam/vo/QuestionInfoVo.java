package com.nju.onlineexam.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionInfoVo {

    String question;
    int type;
    List<Option> optionList;


    public QuestionInfoVo(){
        this.optionList = new ArrayList<>();
    }


    public static class Option {
        public int id;
        public String content;
    }
}
