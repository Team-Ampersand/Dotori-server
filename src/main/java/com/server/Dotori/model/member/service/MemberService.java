package com.server.Dotori.model.member.service;

import com.server.Dotori.model.member.dto.*;

import java.util.Map;

public interface MemberService {
    Long signup(MemberDto memberDto);
    String sendEmailSignup(EmailDto emailDto);
    void checkEmailSignup(SignUpEmailCheckDto memberEmailKeyDto);
    Map<String,String> signIn(SignInDto memberLoginDto);
    String changePassword(ChangePasswordDto memberPasswordDto);
    String sendEmailChangePassword(EmailDto emailDto);
    void checkEmailChangePassword(ChangePasswordEmailCheckDto verifiedAuthKeyAndChangePasswordDto);
    void logout();
    void withdrawal(WithdrawlDto memberDeleteDto);


}
