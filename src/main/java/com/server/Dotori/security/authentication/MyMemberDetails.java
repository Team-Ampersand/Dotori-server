package com.server.Dotori.security.authentication;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.new_exception.DotoriException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.server.Dotori.new_exception.ErrorCode.MEMBER_NOT_FOUND;

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
