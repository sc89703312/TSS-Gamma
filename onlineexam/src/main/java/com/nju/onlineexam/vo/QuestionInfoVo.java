package com.nju.onlineexam.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionInfoVo {

    String question;
    int type;
    List<Option> optionList;


    private class Option {
        public int id;
        public String content;

    }
}
