package com.nju.onlineexam.controller;

import com.nju.onlineexam.dao.CourseRepo;
import com.nju.onlineexam.dao.TeacherRepo;
import com.nju.onlineexam.entity.CourseEntity;
import com.nju.onlineexam.entity.TeacherEntity;
import com.nju.onlineexam.vo.CourseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    CourseRepo courseRepo;
    @Autowired
    TeacherRepo teacherRepo;


    @PostMapping("create")
    public CourseVo createCourse(@RequestHeader(value="UserId") int userId, @RequestBody @Valid CourseVo courseVo){

        if( ! teacherRepo.existsById(userId) ){
            throw new RuntimeException("teacher id not exist:"+userId);
        }

        //读teacher
        TeacherEntity teacherEntity = teacherRepo.getOne(userId);

        //存course
        CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(courseVo,courseEntity);
        courseEntity.setTeacher(teacherEntity);
        courseRepo.save(courseEntity);

        //返回带id的course
        CourseVo resultVo = new CourseVo();
        BeanUtils.copyProperties(courseEntity,resultVo);
        return resultVo;
    }

    @GetMapping("")
    @Transactional
    public List<CourseVo> getCourseList(@RequestHeader(value="UserId") int userId){

        if( ! teacherRepo.existsById(userId) ){
            throw new RuntimeException("teacher id not exist:"+userId);
        }

        //读teacher
        TeacherEntity teacherEntity = teacherRepo.getOne(userId);

        if(teacherEntity.getCourseList().size()==0){
            return new ArrayList<>();
        }else{
            return teacherEntity.getCourseList()
                    .stream()
                    .map(entity -> {
                        CourseVo resultVo = new CourseVo();
                        BeanUtils.copyProperties(entity,resultVo);
                        return resultVo;
                    })
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("{courseId}")
    public CourseVo getCourse(@PathVariable int courseId){

        CourseEntity courseEntity = courseRepo.getOne(courseId);

        CourseVo resultVo = new CourseVo();
        BeanUtils.copyProperties(courseEntity,resultVo);
        return resultVo;
    }

}
