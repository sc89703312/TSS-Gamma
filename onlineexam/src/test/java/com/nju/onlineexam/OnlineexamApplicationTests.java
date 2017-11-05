package com.nju.onlineexam;

import com.nju.onlineexam.dao.QuestionRepo;
import com.nju.onlineexam.dao.StudentExamRepo;
import com.nju.onlineexam.entity.CourseEntity;
import com.nju.onlineexam.entity.QuestionEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import com.nju.onlineexam.entity.TeacherEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineexamApplicationTests {

	@Autowired
	QuestionRepo questionRepo;

	@Autowired
	StudentExamRepo studentExamRepo;

	@Test
	@Transactional
	public void contextLoads() {

//		List<QuestionEntity> questionEntities = questionRepo.findAll();
//		questionEntities.forEach(q -> {
//			System.out.println(q);
//			CourseEntity c = q.getCourse();
//			System.out.println(c);
//			TeacherEntity t = c.getTeacher();
//			System.out.println(t);
//			System.out.println();
//		});

		studentExamRepo.findByExamId(1).forEach(se -> {
			System.out.println(se.getStudent());
			System.out.println(se.getExam());
			System.out.println(se.getStudentExamPaperList());
			System.out.println(se.getExam().getCourse());
			System.out.println(se.getExam().getExamQuestionList());
			se.getExam().getExamQuestionList().forEach(eq -> {
				System.out.println(eq.getQuestion());
				System.out.println(eq.getQuestion().getChoiceList());
			});
		});

	}

}
