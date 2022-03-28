package com.server.Dotori.global.security.authentication;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.exception.DotoriException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.server.Dotori.global.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MyMemberDetails implements UserDetailsService { // UserDetailsService 는 security 의 만들어진 라이브러리기에 명명규칙 X

    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new DotoriException(MEMBER_NOT_FOUND));

        return member;
    }
}
