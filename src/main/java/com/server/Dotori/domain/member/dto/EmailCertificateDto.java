package com.server.Dotori.domain.member.dto;

import com.server.Dotori.domain.member.EmailCertificate;
import com.server.Dotori.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailCertificateDto {

    private Member member;
    private String email;
    private String key;
    private Boolean expired;
    private LocalDateTime expiredTime;

    public EmailCertificate toEntity(String email, String key){
        return EmailCertificate.builder()
                .member(null)
                .email(email)
                .key(key)
                .expiredTime(LocalDateTime.now().plusMinutes(5))
                .build();
    }
}
