package com.server.Dotori.security.authentication;

import com.server.Dotori.exception.user.exception.UserNoInformationException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {
    private final MemberRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Member member = userRepository.findByUsername(nickname);

        if(nickname == null){
            throw new UsernameNotFoundException("nickName '" + nickname + "' not found");
        } else if (member == null){
            throw new UserNoInformationException();
        }

        return member;
    }
}
