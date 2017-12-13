package com.nju.onlineexam.util;

import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.vo.ExamVo;

/**
 * Created by sbin on 2017/12/13.
 */
public class DataConverter {

    public static ExamVo convertToExamVo(ExamEntity examEntity){
        ExamVo examVo = new ExamVo();
        examVo.setId(examEntity.getId());
        examVo.setName(examEntity.getName());
        examVo.setStartTime(examEntity.getStartTime());
        examVo.setEndTime(examEntity.getEndTime());
        return examVo;
    }

}
