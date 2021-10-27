package com.server.Dotori.model.member.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSendServiceImpl implements EmailSendService {

    private final MailSender mailSender;

    /**
     * 이메일로 인증키를 보내주는 서비스 로직
     * @param email email
     * @param key key
     * @author 노경준
     */
    @Override
    public void sendEmail(String email, String key) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[DOTORI] 인증 키");
        message.setText("DOTORI 에서 보낸 인증 키 : " + key);
        mailSender.send(message);
    }

    /**
     * 이메일로 임시 비밀번호를 전송하는 서비스 로직
     * @param email email
     * @param password password
     * @author 노경준
     */
    @Override
    public void sendPasswordEmail(String email, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[DOTORI] 임시 비밀번호");
        message.setText("DOTORI 에서 보낸 임시 비밀번호 : " + password);
        mailSender.send(message);
    }

}
