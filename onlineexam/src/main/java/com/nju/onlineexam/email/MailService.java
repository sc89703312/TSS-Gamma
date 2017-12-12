package com.nju.onlineexam.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 异步化
 */
@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(String from , String to , String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);
        mailSender.send(message);
    }


}
