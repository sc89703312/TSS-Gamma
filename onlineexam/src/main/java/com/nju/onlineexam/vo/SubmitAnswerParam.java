package com.nju.onlineexam.vo;

import lombok.Data;

import java.util.List;

@Data
public class SubmitAnswerParam {

    List<QuestionAndAnswer> answer;

    @Data
    public  class QuestionAndAnswer{
        int questionId;
        int[] answerIds;
    }
}
