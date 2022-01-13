package com.server.Dotori.security;

import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SecurityTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void tokenTest() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberName("노경준");
        memberDto.setStuNum("2206");
        memberDto.setPassword("1234");
        memberDto.setEmail("s20018@gsm.hs.kr");

        String accessToken = jwtTokenProvider.createToken(memberDto.getEmail(), memberDto.toEntity().getRoles());
        // 유효한 토큰인지 확인
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)){
            String email = jwtTokenProvider.getEmail(accessToken);
            Assertions.assertThat(email).isEqualTo("s20018@gsm.hs.kr");
        }
    }
}
