package com.server.Dotori.model.member.service.email;

import com.server.Dotori.model.member.service.email.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSendServiceImpl implements EmailSendService {

    private final MailSender mailSender;

    /**
     * 인증 키 보내기
     * @param userEmail 이메일
     * @param key 인증키
     * @author 노경준
     */
    @Override
    public void sendEmail(String userEmail, String key) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("[DOTORI] 인증 키");
        message.setText("DOTORI 에서 보낸 인증 키 : " + key);
        mailSender.send(message);
    }

    /**
     * 임시 비밀번호 발급
     * @param userEmail 이메일
     * @param password 현재 비밀번호
     * @author 노경준
     */
    @Override
    public void sendPasswordEmail(String userEmail, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("[DOTORI] 임시 비밀번호");
        message.setText("DOTORI 에서 보낸 임시 비밀번호 : " + password);
        mailSender.send(message);
    }

}
