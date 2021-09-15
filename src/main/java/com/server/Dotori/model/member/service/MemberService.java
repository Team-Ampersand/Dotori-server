package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.MemberLoginDto;
import com.server.Dotori.model.member.dto.MemberPasswordDto;

import java.util.Map;

public interface MemberService {
    Long signup(MemberDto memberDto);
    Map<String,String> signin(MemberLoginDto memberLoginDto);
    Map<String,String> passwordChange(MemberPasswordDto memberPasswordDto);
    void logout();
}
