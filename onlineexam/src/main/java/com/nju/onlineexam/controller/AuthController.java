package com.nju.onlineexam.controller;

import com.nju.onlineexam.dao.StudentRepo;
import com.nju.onlineexam.dao.TeacherRepo;
import com.nju.onlineexam.entity.StudentEntity;
import com.nju.onlineexam.entity.TeacherEntity;
import com.nju.onlineexam.model.LoginParam;
import com.nju.onlineexam.model.RegisterParam;
import com.nju.onlineexam.service.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    TeacherRepo teacherRepo;

    @PostMapping("/login")
    public void login(@RequestBody LoginParam loginParam , HttpSession httpSession){

        StudentEntity student = studentRepo.findByEmail(loginParam.email);
        TeacherEntity teacher = teacherRepo.findByEmail(loginParam.email);
        if(student == null && teacher == null){
            throw new RuntimeException("账号不存在");
        }

        student = studentRepo.findByEmailAndPassword(loginParam.email, loginParam.password);
        if(student != null ){
            httpSession.setAttribute(Config.USER_KEY , student);
            httpSession.setAttribute(Config.ROLE_KEY , Const.STUDENT);
        }else{
            teacher = teacherRepo.findByEmailAndPassword(loginParam.email, loginParam.password);
            if(teacher != null){
                httpSession.setAttribute(Config.USER_KEY , teacher);
                httpSession.setAttribute(Config.ROLE_KEY , Const.TEACHER);
            }else {
                throw new RuntimeException("密码错误");
            }
        }
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterParam registerParam){
        StudentEntity student = studentRepo.findByEmail(registerParam.email);
        TeacherEntity teacher = teacherRepo.findByEmail(registerParam.email);
        if( student != null || teacher != null ){
            throw new RuntimeException("该邮箱已注册");
        }

        if(registerParam.role == Const.STUDENT){
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName(registerParam.name);
            studentEntity.setEmail(registerParam.email);
            studentEntity.setPassword(registerParam.password);
            studentEntity.setNumber(registerParam.number);
            studentRepo.save(studentEntity);
        }else if(registerParam.role == Const.TEACHER){
            TeacherEntity teacherEntity = new TeacherEntity();
            teacherEntity.setName(registerParam.name);
            teacherEntity.setEmail(registerParam.email);
            teacherEntity.setPassword(registerParam.password);
            teacherRepo.save(teacherEntity);
        }else {
            throw new RuntimeException("未知的角色类型");
        }

    }




}
