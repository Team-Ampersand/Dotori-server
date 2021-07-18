package com.server.Dotori.model.member.service.Impl;

import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.server.Dotori.model.member.enumType.Role.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
        if(memberRepository.findByUsername(memberDto.getUsername()) != null){
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
        return 0L;
    }
}
