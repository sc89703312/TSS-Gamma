package com.nju.onlineexam.vo;

import lombok.Data;

import java.util.Map;

@Data
public class SubmitAnswerParam {

    Map<Integer, int []> answer;
}
