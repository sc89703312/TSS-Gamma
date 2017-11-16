package com.nju.onlineexam;

import com.nju.onlineexam.dao.QuestionRepo;
import com.nju.onlineexam.dao.StudentExamRepo;
import com.nju.onlineexam.entity.CourseEntity;
import com.nju.onlineexam.entity.QuestionEntity;
import com.nju.onlineexam.entity.StudentExamEntity;
import com.nju.onlineexam.entity.TeacherEntity;
import com.nju.onlineexam.excel.ExcelHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineexamApplicationTests {

	@Autowired
	QuestionRepo questionRepo;

	@Autowired
	StudentExamRepo studentExamRepo;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	ExcelHelper excelHelper;


	@Test
	@Transactional
	public void contextLoads() {

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

	@Test
	public void sendMail(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("13260905180@163.com");	//must equal auth user
		message.setTo("141250106@smail.nju.edu.cn");
		message.setText("test message");
		message.setSubject("test message");
		mailSender.send(message);
	}

	@Test
	public void readExcel() throws FileNotFoundException {
		File file = new File("test_excel/test.xlsx");
		FileInputStream fiStream = new FileInputStream(file);

		List<Map<String,String>> voList = excelHelper.convertToVo(fiStream,file.getName(),2, l->{
			System.out.println("field list:"+l);
			Map<String,String> map = new LinkedHashMap<>();
			map.put("name",l.get(0));
			map.put("number",l.get(1));
			map.put("score",l.get(2));
			return map;
		});

		System.out.println(voList);
		assertEquals(3,voList.size());

	}

	@Test
	public void writeExcel() throws IOException {

		List<StudentPaper> papers = new ArrayList<>();
		{
			papers.add(new StudentPaper("111","q1","a1"));
			papers.add(new StudentPaper("222","q2","a2"));
		}

		XSSFWorkbook workbook = new XSSFWorkbook("test_excel/test_write.xlsx");
		excelHelper.convertToExcel(workbook,2, papers , s->{
			List<String> l = new ArrayList<>();
			l.add(s.number);
			l.add(s.question);
			l.add(s.answer);
			return l;
		});

		workbook.write(new FileOutputStream("test_excel/test_write_a.xlsx"));

	}

	private class StudentPaper{
		String number;
		String question;
		String answer;

		public StudentPaper(String number, String question, String answer) {
			this.number = number;
			this.question = question;
			this.answer = answer;
		}
	}

}
