package com.nju.onlineexam.service;

import com.nju.onlineexam.excel.ExcelHelper;
import com.nju.onlineexam.vo.ChoiceVo;
import com.nju.onlineexam.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionExcelReader {

    @Autowired
    ExcelHelper excelHelper;

    public List<QuestionVo> readExcel(InputStream excelInputStream,String fileName){
        List<QuestionVo> questionVos =
                excelHelper.convertToObj(excelInputStream,fileName,1, list ->{

                    if(list.size() < 3){
                        throw new RuntimeException("information not enough:"+list.toString());
                    }

                    //题干
                    QuestionVo questionVo = new QuestionVo();
                    questionVo.setDescription(list.get(0));

                    //设置多选
                    String mutiAnswerCell = list.get(1);
                    if( "是".equals(mutiAnswerCell) ){
                        questionVo.setMultiAnswer(true);
                    }else if("否".equals(mutiAnswerCell) ) {
                        questionVo.setMultiAnswer(false);
                    }

                    //设置正确答案的坐标, 将"A B"转为[0,1]
                    List<Integer> answerIndexList = new ArrayList<>();
                    for( String answer : list.get(2).split(" ") ){

                        if( answer.length() != 1 ){
                            throw new RuntimeException("answer not valid:"+list.get(2) );
                        }

                        int index = answer.charAt(0) - 'A';
                        if( index < 0 || index >25  ){
                            throw new RuntimeException("answer not valid:"+list.get(2) );

                        }

                        answerIndexList.add(index);
                    }

                    //读取选项答案
                    ArrayList<ChoiceVo> choiceVos = new ArrayList<>();
                    for( int i = 3 ; i< list.size() ; i++ ){
                        ChoiceVo choiceVo = new ChoiceVo();
                        choiceVo.setDescription(list.get(i));
                        choiceVos.add(choiceVo);
                    }
                    for(int i:answerIndexList){
                        choiceVos.get(i).setRightAnswer(true);
                    }

                    questionVo.setChoices(choiceVos);
                    return questionVo;
                });

        return questionVos;
    }

}
