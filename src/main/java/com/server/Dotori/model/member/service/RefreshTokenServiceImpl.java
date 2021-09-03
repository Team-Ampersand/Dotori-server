package com.server.Dotori.model.member.service;

import com.server.Dotori.exception.token.exception.LogoutTokenException;
import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.MemberLoginDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.service.RefreshTokenService;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import com.server.Dotori.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;

    @Override
    public Map<String, String> getRefreshToken(HttpServletRequest request) {

        String accessToken = jwtTokenProvider.resolveToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String username = jwtTokenProvider.getUsername(accessToken);
        Member memberInfo = memberRepository.findByUsername(username);
        List<Role> roles = memberInfo.getRoles();
        Map<String,String> map = new HashMap<>();
        String newAccessToken = null;
        String newRefreshToken = null;

        if (redisUtil.getData(username) == null) {
            throw new LogoutTokenException();
        }

        if(redisUtil.getData(username).equals(refreshToken) && jwtTokenProvider.validateToken(refreshToken)){
            redisUtil.deleteData(username);
            newAccessToken = jwtTokenProvider.createToken(username,roles);
            newRefreshToken = jwtTokenProvider.createRefreshToken();
            redisUtil.setDataExpire(username,newRefreshToken,jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
            map.put("username", username);
            map.put("NewAccessToken", "Bearer " + newAccessToken); // NewAccessToken 반환
            map.put("NewRefreshToken", "Bearer " + newRefreshToken); // NewRefreshToken 반환

            return map;
        }

        return map;
    }
}
