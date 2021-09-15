package com.server.Dotori.security;

import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.security.authentication.MyUserDetails;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import com.server.Dotori.util.redis.RedisUtil;
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
    @Autowired
    private MyUserDetails myUserDetails;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void tokenTest() {
        MemberDto memberDto = new MemberDto();
        memberDto.setUsername("노경준");
        memberDto.setStdNum("2206");
        memberDto.setPassword("1234");
        memberDto.setEmail("shrudwns@naver.com");

        String accessToken = jwtTokenProvider.createToken(memberDto.getUsername(), memberDto.toEntity().getRoles());
        // 유효한 토큰인지 확인
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)){
            String nickname = jwtTokenProvider.getUsername(accessToken);
            Assertions.assertThat(nickname).isEqualTo("노경준");
        }
    }
}
