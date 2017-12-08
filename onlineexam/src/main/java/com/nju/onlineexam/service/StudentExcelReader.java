package com.nju.onlineexam.service;

import com.nju.onlineexam.dao.StudentRepo;
import com.nju.onlineexam.entity.StudentEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import com.nju.onlineexam.excel.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * Created by sbin on 2017/12/7.
 */
@Component
public class StudentExcelReader {

    @Autowired
    ExcelHelper excelHelper;

    @Autowired
    StudentRepo studentRepo;

    //读取并校验学生名单，转化为entity
    public List<StudentExamEntity> readExcel(InputStream excelInputStream, String fileName) {

        List<StudentExamEntity> entities =
                excelHelper.convertToObj(excelInputStream,fileName,1,list->{

            StudentEntity student = studentRepo.findByEmail(list.get(1));

            StudentExamEntity studentExam = new StudentExamEntity();
            studentExam.setStudent(student);
            return studentExam;

        });
        return entities;
    }
}