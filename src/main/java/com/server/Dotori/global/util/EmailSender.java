package com.server.Dotori.global.util;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.server.Dotori.domain.member.dto.SenderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        String subject = "🎈[DOTORI] 인증 키";
        String message = "<p style=\"color:blueviolet\">안녕하세요 Dotori 계정에 사용할 일회용 코드에 대한 요청을 받았습니다.</p>";;
            message += "<p>일회용 코드: " + key + "</p>";
            message += "<p>이 코드를 요청하지 않은 경우 이 메일을 무시하셔도 됩니다. 다른 사람이 실수로 귀하의 이메일 주소를 입력했을 수 있습니다.</p>";
            message += "<p>감사합니다 Dotori 계정 팀</p>";
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
