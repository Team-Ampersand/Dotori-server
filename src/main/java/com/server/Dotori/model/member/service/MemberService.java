package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.*;

import java.util.Map;

public interface MemberService {
    Long signup(MemberDto memberDto);
    Map<String,String> signin(MemberLoginDto memberLoginDto);
    Map<String,String> passwordChange(MemberPasswordDto memberPasswordDto);
    void BeforeLoginPasswordChange(String email);
    void BeforeLoginPasswordChangeCheck(BeforeLoginPasswordChangeCheckDto beforeLoginPasswordChangeCheckDto);
    void logout();
    void delete(MemberDeleteDto memberDeleteDto);
}
