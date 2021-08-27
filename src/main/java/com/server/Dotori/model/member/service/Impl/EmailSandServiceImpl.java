package com.server.Dotori.model.member.service.Impl;

import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.service.EmailSandService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSandServiceImpl implements EmailSandService {

    private final MailSender mailSender;
    @Override
    public void sandEmail(String userEmail, String key) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("[DOTORI] 인증 키");
        message.setText("DOTORI 에서 보낸 인증 키 : " + key);
        mailSender.send(message);
    }

    @Override
    public void sandPasswordEmail(String userEmail, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("[DOTORI] 임시 비밀번호");
        message.setText("DOTORI 에서 보낸 임시 비밀번호 : " + password);
        mailSender.send(message);
    }


}
