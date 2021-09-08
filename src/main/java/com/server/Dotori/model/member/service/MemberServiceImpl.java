package com.server.Dotori.model.member.service;

import com.server.Dotori.exception.user.exception.UserAlreadyException;
import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.MemberLoginDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import com.server.Dotori.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.server.Dotori.model.member.enumType.Role.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    /**
     * 회원가입
     * @param memberDto memberDto
     * @return result.getId()
     */
    @Override
    public Long signup(MemberDto memberDto){
        if(memberRepository.findByEmail(memberDto.getEmail()) == null && memberRepository.findByStdNum(memberDto.getStdNum()) == null){
            memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
            Member result = memberRepository.save(memberDto.toEntity());
            return result.getId();
        }else {
            throw new UserAlreadyException();
        }
    }

    /**
     * 로그인
     * @param memberLoginDto memberLoginDto
     * @return
     */
    @Override
    public Map<String,String> signin(MemberLoginDto memberLoginDto) {
        Member findUser = memberRepository.findByUsername(memberLoginDto.getEmail());
        if(findUser == null) throw new UserNotFoundException();

        boolean passwordCheck = passwordEncoder.matches(memberLoginDto.getPassword(),findUser.getPassword());
        if(!passwordCheck) throw new UserNotFoundException();

        Map<String,String> map = new HashMap<>();
        String accessToken = jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisUtil.deleteData(memberLoginDto.getEmail());
        redisUtil.setDataExpire(memberLoginDto.getEmail(),refreshToken,jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);

        map.put("username",findUser.getUsername());
        map.put("accessToken","Bearer " + accessToken);
        map.put("refreshToken","Bearer " + refreshToken);

        return map;
    }

}
