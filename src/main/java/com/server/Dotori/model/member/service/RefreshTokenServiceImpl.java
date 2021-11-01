package com.server.Dotori.model.member.service;

import com.server.Dotori.exception.token.exception.LogoutTokenException;
import com.server.Dotori.exception.token.exception.RefreshTokenFailException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import com.server.Dotori.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;

    /**
     * RefreshToken으로 AccessToken과 RefreshToken을 재발급 시켜주는 서비스 로직
     * @param username username
     * @param refreshToken refreshToken
     * @return map - username, accessToken, refreshToken
     * @author 노경준
     */
    @Override
    public Map<String, String> getRefreshToken(String username, String refreshToken) {
        Member findMember = memberRepository.findByUsername(username);
        List<Role> roles = findMember.getRoles();

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

        throw new RefreshTokenFailException();
    }
}
