package com.nju.onlineexam.email;

import com.nju.onlineexam.dao.ExamRepo;
import com.nju.onlineexam.dao.StudentRepo;
import com.nju.onlineexam.entity.ExamEntity;
import com.nju.onlineexam.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class InformTask {

    private final static long INTERVAL   = 5 * 60 * 1000;
    private final static long TIME_AHEAD = 15 * 60 * 1000;


    @Value("${email.from-addr}")
    private String fromAddr;

    @Value("${email.subject}")
    private String subject;

    @Autowired
    ExamRepo examRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    MailService mailService;

    @Scheduled(fixedRate = INTERVAL)
    public void inform(){
        long now = System.currentTimeMillis();
        String start = DateHelper.TimestampToString( new Timestamp(now + TIME_AHEAD-INTERVAL)) ;
        String end =  DateHelper.TimestampToString(new Timestamp(now + TIME_AHEAD)) ;
        List<ExamEntity>  examEntities = examRepo.findExamsStartBetween(start ,end);
        for(ExamEntity examEntity : examEntities){
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("您有一场考试：").append(examEntity.getName()).append("，即将在").append(examEntity.getStartTimeInString()).append(" 开始，请提前做好准备。");
            List<String> studentEmails = examRepo.findStdEmailsByExamId(examEntity.getId());
            for(String toAddr : studentEmails) {
                mailService.sendEmail(fromAddr, toAddr, subject, messageBuilder.toString());
            }
        }
    }



}
