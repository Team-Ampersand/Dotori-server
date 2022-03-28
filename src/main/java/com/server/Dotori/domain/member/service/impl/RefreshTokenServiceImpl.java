package com.server.Dotori.domain.member.service.impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.dto.RefreshTokenDto;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.service.RefreshTokenService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.server.Dotori.global.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    /**
     * RefreshToken 으로 AccessToken 과 RefreshToken 을 재발급 시켜주는 서비스 로직
     * @param refreshToken refreshToken
     * @return map - email, accessToken, refreshToken
     * @author 노경준
     */
    @Transactional
    @Override
    public Map<String, String> refreshToken(String refreshToken, RefreshTokenDto refreshTokenDto) {
        String email = refreshTokenDto.getEmail();

        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DotoriException(MEMBER_NOT_FOUND));

        if (findMember.getRefreshToken() == null) {
            throw new DotoriException(TOKEN_INVALID);
        }

        List<Role> roles = findMember.getRoles();

        Map<String,String> map = new HashMap<>();
        String newAccessToken = null;
        String newRefreshToken = null;

        if(findMember.getRefreshToken().equals(refreshToken) && jwtTokenProvider.validateToken(refreshToken)){
            newAccessToken = jwtTokenProvider.createToken(email, roles);
            newRefreshToken = jwtTokenProvider.createRefreshToken();
            findMember.updateRefreshToken(newRefreshToken);
            map.put("email", email);
            map.put("NewAccessToken", "Bearer " + newAccessToken);
            map.put("NewRefreshToken", "Bearer " + newRefreshToken);
            return map;
        }

        throw new DotoriException(TOKEN_REFRESH_FAIL);
    }
}
