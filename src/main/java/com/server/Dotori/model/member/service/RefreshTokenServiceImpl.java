package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.new_exception.DotoriException;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.server.Dotori.new_exception.ErrorCode.*;

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
    public Map<String, String> getRefreshToken(String refreshToken) {
        String email = jwtTokenProvider.getEmail(refreshToken);

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
            newRefreshToken = jwtTokenProvider.createRefreshToken(email, roles);
            findMember.updateRefreshToken(newRefreshToken);
            map.put("email", email);
            map.put("NewAccessToken", "Bearer " + newAccessToken); // NewAccessToken 반환
            map.put("NewRefreshToken", "Bearer " + newRefreshToken); // NewRefreshToken 반환
            return map;
        }

        throw new DotoriException(TOKEN_REFRESH_FAIL);
    }
}
