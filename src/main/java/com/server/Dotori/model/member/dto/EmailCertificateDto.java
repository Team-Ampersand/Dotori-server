package com.server.Dotori.model.member.dto;

import com.server.Dotori.model.member.EmailCertificate;
import com.server.Dotori.model.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailCertificateDto {

    private Member member;
    private String email;
    private String key;
    private Boolean expired;
    private LocalDateTime expiredTime;

    public EmailCertificate toEntity(String email,String key){
        return EmailCertificate.builder()
                .member(null)
                .email(email)
                .key(key)
                .expiredTime(LocalDateTime.now().plusMinutes(5))
                .build();
    }
}
