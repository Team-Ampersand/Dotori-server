package com.server.Dotori.model.member.service.email;

import com.server.Dotori.exception.user.exception.OverCertificateTimeException;
import com.server.Dotori.exception.user.exception.UserAlreadyException;
import com.server.Dotori.exception.user.exception.UserAuthenticationKeyNotMatchingException;
import com.server.Dotori.model.member.EmailCertificate;
import com.server.Dotori.model.member.dto.EmailCertificateDto;
import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;
import com.server.Dotori.model.member.repository.email.EmailCertificateRepository;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.util.EmailSender;
import com.server.Dotori.util.KeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final KeyUtil keyUtil;
    private final EmailSender emailSender;
    private final MemberRepository memberRepository;
    private final EmailCertificateRepository emailCertificateRepository;

    /**
     * 회원가입할때 이메일로 인증번호를 전송하는 기능
     * @param emailDto email
     * @return 인증번호
     * @author 노경준
     */
    @Override
    public String authKey(EmailDto emailDto) {
        String email = emailDto.getEmail();

        if(!memberRepository.existsByEmail(email)){
            String key = keyUtil.keyIssuance();

            EmailCertificateDto emailCertificateDto = new EmailCertificateDto();
            EmailCertificate emailCertificate = emailCertificateDto.toEntity(email, key);

            emailCertificateRepository.deleteEmailCertificateByEmail(email);
            emailCertificateRepository.save(emailCertificate);
            emailSender.send(email,key);
            return key;
        }else{
            throw new UserAlreadyException();
        }
    }

    /**
     * 회원가입할때 authKey에서 보내준 키와 일치한지 인증하는 기능
     * @param memberEmailKeyDto key
     * @return 입력된 키
     * @author 노경준
     */
    @Override
    public String authCheck(MemberEmailKeyDto memberEmailKeyDto) {
        String key = memberEmailKeyDto.getKey();
        EmailCertificate emailCertificate = emailCertificateRepository.findByKey(key).orElseThrow(UserAuthenticationKeyNotMatchingException::new);

        if(emailCertificate.getExpiredTime().isAfter(LocalDateTime.now())){
            emailCertificateRepository.deleteEmailCertificateByKey(key);
            return key;
        }else{
            emailCertificateRepository.deleteEmailCertificateByKey(key);
            throw new OverCertificateTimeException();
        }
    }
}
