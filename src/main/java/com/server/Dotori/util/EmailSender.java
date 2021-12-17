package com.server.Dotori.util;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.server.Dotori.model.member.dto.SenderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSender {
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Value("${aws.ses.veritied.email}")
    private String from;

    /**
     * 이메일 전송
     */
    public void send(String receivers, String key) {
        String subject = "[DOTORI] 인증 키";
        String message = "도토리 인증 키 : " + key + "<br> 이 인증키를 외부에 노출하지 마세오.";
        if(receivers == null) {
            log.error("메일을 전송할 대상이 없습니다: [{}]", subject);
            return;
        }

        SenderDto senderDto = SenderDto.builder()
                .from(from)
                .to(receivers)
                .subject(subject)
                .content(message)
                .build();

        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(senderDto.toSendRequestDto());

        if(sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
            log.info("[AWS SES] 메일전송완료 => " + senderDto.getTo());
        }else {
            log.error("[AWS SES] 메일전송 중 에러가 발생했습니다: {}", sendEmailResult.getSdkResponseMetadata().toString());
            log.error("발송실패 대상자: " + senderDto.getTo() + " / subject: " + senderDto.getSubject());
        }
    }

}
