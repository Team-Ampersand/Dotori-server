package com.server.Dotori.model.member.service.Impl;

import com.server.Dotori.model.member.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sandEmail(String userEmail, String key) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("[DOTORI] 인증 키");
        message.setText("DOTORI 에서 보낸 인증 키 : " + key);
        mailSender.send(message);
    }
}
