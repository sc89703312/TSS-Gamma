package com.nju.onlineexam.util;

import com.nju.onlineexam.dao.ExamQuestionRepo;
import com.nju.onlineexam.dao.QuestionRepo;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.entity.ExamQuestionEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import com.nju.onlineexam.entity.StudentExamPaperEntity;
import com.nju.onlineexam.vo.ExamVo;
import com.nju.onlineexam.vo.StudentPaperRowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sbin on 2017/12/13.
 */
@Component
public class DataConverter {

    @Autowired
    ExamQuestionRepo examQuestionRepo;

    @Autowired
    QuestionRepo questionRepo;


    public  ExamVo convertToExamVo(ExamEntity examEntity){
        ExamVo examVo = new ExamVo();
        examVo.setId(examEntity.getId());
        examVo.setName(examEntity.getName());
        examVo.setStartTime(examEntity.getStartTime());
        examVo.setEndTime(examEntity.getEndTime());
        return examVo;
    }

    public  List<StudentPaperRowVo> convertToStudentPaperRowVo(StudentExamEntity studentExamEntity){

        int examId = studentExamEntity.getExam().getId();
        List<StudentExamPaperEntity> studentExamPaperEntities = studentExamEntity.getStudentExamPaperList();

        List<StudentPaperRowVo> studentPaperRowVos = studentExamPaperEntities.stream()
            .map(paperEntity -> {
            StudentPaperRowVo rowVo = new StudentPaperRowVo();
            rowVo.setQuestion(paperEntity.getQuestion().getDescription());

            int questionId = paperEntity.getQuestion().getId();
            ExamQuestionEntity examQuestionEntity = this.examQuestionRepo.getByQuestionIdAndExamId(questionId,examId);
            rowVo.setScore(examQuestionEntity.getScore());
            rowVo.setRight(paperEntity.getIsRight());
            List<Integer> allChoiceIds = this.questionRepo.findChoiceIds(questionId);
            List<Integer> rightChoiceIds = this.questionRepo.findRightChoiceIds(questionId);
            rowVo.setCorrectAnswer(this.getRelativeOrderOfAnswers(allChoiceIds,rightChoiceIds));
            List<Integer> selectedIds = this.stringToList(paperEntity.getSelected());
            rowVo.setSelected(this.getRelativeOrderOfAnswers(allChoiceIds,selectedIds));
            rowVo.setChoicesInOrder(this.questionRepo.findChoiceContents(questionId));
            return rowVo;
        }).collect(Collectors.toList());

        return studentPaperRowVos;
    }


    private String getRelativeOrderOfAnswers(List<Integer> allChoiceIds , List<Integer> partChoiceIds ){
        if(partChoiceIds == null || partChoiceIds.size() == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for(Integer partId : partChoiceIds){
            int index = allChoiceIds.indexOf(partId);
            sb.append( (char)(index+'A') ).append(",");
        }

        if(sb.length()>1){
            sb.deleteCharAt(sb.length()-1);
        }

        return sb.toString();
    }

    private List<Integer> stringToList(String selected){
        if(selected == null || selected.length() == 0){
            return new ArrayList<>();
        }

        List<Integer> idList = new ArrayList<>();
        String[] idArray = selected.split(",");
        for(int i = 0 ; i < idArray.length ; i++){
            idList.add(Integer.parseInt(idArray[i]));
        }
        return idList;
    }

}
