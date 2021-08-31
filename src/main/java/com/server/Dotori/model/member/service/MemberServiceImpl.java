package com.server.Dotori.model.member.service;

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

    @Value("${authKey.adminKey}")
    private String adminKey;
    @Value("${authKey.councillorKey}")
    private String councillorKey;
    @Value("${authKey.memberKey}")
    private String memberKey;
    @Value("${authKey.developerKey}")
    private String developerKey;

    @Override
    public Long signup(MemberDto memberDto){
        if(memberRepository.existsByUsername(memberDto.getUsername())){
            throw new UserNotFoundException();
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        if(memberDto.getKey().equals(adminKey)){
            Member userInfo = memberRepository.save(memberDto.toEntity(ROLE_ADMIN));
            return userInfo.getId();
        }else if(memberDto.getKey().equals(councillorKey)){
            Member userInfo = memberRepository.save(memberDto.toEntity(ROLE_COUNCILLOR));
            return userInfo.getId();
        }else if(memberDto.getKey().equals(memberKey)){
            Member userInfo = memberRepository.save(memberDto.toEntity(ROLE_MEMBER));
            return userInfo.getId();
        }else if(memberDto.getKey().equals(developerKey)){
            Member userInfo = memberRepository.save(memberDto.toEntity(ROLE_DEVELOPER));
            return userInfo.getId();
        }
        Member findUser = memberRepository.findByUsername(memberDto.getUsername());
        return findUser.getId();
    }

    @Override
    public Map<String,String> signin(MemberLoginDto memberLoginDto) {
        Member findUser = memberRepository.findByUsername(memberLoginDto.getUsername());
        if(findUser == null) throw new UserNotFoundException();

        boolean passwordCheck = passwordEncoder.matches(memberLoginDto.getPassword(),findUser.getPassword());
        if(!passwordCheck) throw new UserNotFoundException();

        Map<String,String> map = new HashMap<>();
        String accessToken = jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisUtil.deleteData(memberLoginDto.getUsername());
        redisUtil.setDataExpire(memberLoginDto.getUsername(),refreshToken,jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);

        map.put("username",findUser.getUsername());
        map.put("accessToken","Bearer " + accessToken);
        map.put("refreshToken","Bearer " + refreshToken);

        return map;
    }

}
