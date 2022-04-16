package com.server.Dotori.domain.member.service;

import com.server.Dotori.domain.member.dto.*;

public interface MemberService {
    Long signup(MemberDto memberDto);
    String sendEmailSignup(EmailDto emailDto);
    void checkEmailSignup(SignUpEmailCheckDto memberEmailKeyDto);
    SignInResponseDto signIn(SignInDto memberLoginDto);
    String changePassword(ChangePasswordDto changePasswordDto);
    String sendEmailChangePassword(EmailDto emailDto);
    void checkEmailChangePassword(ChangePasswordEmailCheckDto verifiedAuthKeyAndChangePasswordDto);
    void logout();
    void withdrawal(WithdrawlDto memberDeleteDto);
    void setGender(SetGenderDto setGenderDto);
}
