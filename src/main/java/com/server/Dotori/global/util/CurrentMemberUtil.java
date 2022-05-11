package com.server.Dotori.global.util;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.exception.DotoriException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.server.Dotori.global.exception.ErrorCode.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CurrentMemberUtil {

    private final MemberRepository memberRepository;

    public static String getMemberName(){
        String memberName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            memberName = ((Member) principal).getMemberName();
        } else{
            memberName = principal.toString();
        }
        return memberName;
    }

    public static String getCurrentEmail(){
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            email = ((Member) principal).getEmail();
        } else{
            email = principal.toString();
        }
        return email;
    }

    public Member getCurrentMember() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            email = ((Member) principal).getEmail();
        } else{
            email = principal.toString();
        }
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new DotoriException(MEMBER_NOT_FOUND));
    }
}