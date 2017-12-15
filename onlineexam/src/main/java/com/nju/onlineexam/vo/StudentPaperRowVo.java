package com.nju.onlineexam.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentPaperRowVo {
    String question;
    int score;
    boolean isRight;
    /**
     * A,B
     */
    String correctAnswer;
    /**
     * A
     */
    String selected;

    List<String> choicesInOrder;
}
