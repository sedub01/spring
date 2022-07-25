package com.github.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SmtpMailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;

    /**
     * Метод, рассылающий почту
     * @param mailTo адресат
     * @param subject тема письма
     * @param message тело письма
     */
    public  void send(String mailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(mailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        //Если выдает ошибку о "timeout -1", выключите ВПН (у меня так сработало)
        mailSender.send(mailMessage);
    }
}
