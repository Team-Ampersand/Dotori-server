package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.*;

import java.util.Map;

public interface MemberService {
    Long signup(MemberDto memberDto);
    Map<String,String> signin(MemberLoginDto memberLoginDto);
    String passwordChange(MemberPasswordDto memberPasswordDto);
    void sendAuthKeyForChangePassword(SendAuthKeyForChangePasswordDto sendAuthKeyForChangePasswordDto);
    void sendAuthKeyForChangePasswordCheck(SendAuthKeyForChangePasswordCheckDto sendAuthKeyForChangePasswordCheckDto);
    void logout();
    void delete(MemberDeleteDto memberDeleteDto);
}
