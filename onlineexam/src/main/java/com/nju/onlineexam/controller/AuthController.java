package com.nju.onlineexam.controller;

import com.nju.onlineexam.dao.StudentRepo;
import com.nju.onlineexam.dao.TeacherRepo;
import com.nju.onlineexam.entity.StudentEntity;
import com.nju.onlineexam.entity.TeacherEntity;
import com.nju.onlineexam.model.LoginParam;
import com.nju.onlineexam.model.RegisterParam;
import com.nju.onlineexam.model.ResponseBase;
import com.nju.onlineexam.service.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AuthController {

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    TeacherRepo teacherRepo;

    @PostMapping("/login")
    public ResponseBase login(@RequestBody LoginParam loginParam , HttpSession httpSession){
        List<StudentEntity> students = studentRepo.findByEmailAndPassword(loginParam.email, loginParam.password);
        if(students != null && students.size() > 0){
            httpSession.setAttribute(Config.USER_KEY , students.get(0));
            httpSession.setAttribute(Config.ROLE_KEY , Const.STUDENT);
            return  new ResponseBase(Const.SUCC_RET, null , null );
        }else{
            List<TeacherEntity> teachers = teacherRepo.findByEmailAndPassword(loginParam.email, loginParam.password);
            if(teachers != null && teachers.size() > 0){
                httpSession.setAttribute(Config.USER_KEY , teachers.get(0));
                httpSession.setAttribute(Config.ROLE_KEY , Const.TEACHER);
                return  new ResponseBase(Const.SUCC_RET, null , null );
            }else {
                return  new ResponseBase(Const.FAIL_RET , "找不到该账号" , null);
            }
        }
    }

    @PostMapping("/register")
    public ResponseBase register(@RequestBody RegisterParam registerParam){
        List<StudentEntity> students = studentRepo.findByEmailAndPassword(registerParam.email, registerParam.password);
        List<TeacherEntity> teachers = teacherRepo.findByEmailAndPassword(registerParam.email, registerParam.password);
        if(( students != null && students.size() > 0)|| (teachers != null && teachers.size() > 0 )){
            return new ResponseBase(Const.FAIL_RET , "已经存在相同邮箱的账号" , null);
        }

        if(registerParam.role == Const.STUDENT){

            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName(registerParam.name);
            studentEntity.setEmail(registerParam.email);
            studentEntity.setPassword(registerParam.password);
            studentEntity.setNumber(registerParam.number);
            studentRepo.save(studentEntity);
            return  new ResponseBase(Const.SUCC_RET, null , null );


        }else if(registerParam.role == Const.TEACHER){

            TeacherEntity teacherEntity = new TeacherEntity();
            teacherEntity.setName(registerParam.name);
            teacherEntity.setEmail(registerParam.email);
            teacherEntity.setPassword(registerParam.password);
            teacherRepo.save(teacherEntity);
            return  new ResponseBase(Const.SUCC_RET, null , null );

        }else {
            return new ResponseBase(Const.FAIL_RET , "角色类型不匹配:"+registerParam.role , null);
        }

    }




}
