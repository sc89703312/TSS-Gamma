package com.nju.onlineexam.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 异步化
 */
@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;


}
