package com.server.Dotori.global.security;

import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.global.security.jwt.JwtTokenProvider;
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
        // given
        MemberDto memberDto = MemberDto.builder()
                .memberName("노경준")
                .stuNum("2206")
                .password("1234")
                .email("s20018@gsm.hs.kr")
                .build();

        // when
        String accessToken = jwtTokenProvider.createToken(memberDto.getEmail(), memberDto.toEntity(memberDto.getPassword(), Gender.MAN).getRoles());

        // then
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)){
            String email = jwtTokenProvider.getEmail(accessToken);
            Assertions.assertThat(email).isEqualTo("s20018@gsm.hs.kr");
        }
    }
}
