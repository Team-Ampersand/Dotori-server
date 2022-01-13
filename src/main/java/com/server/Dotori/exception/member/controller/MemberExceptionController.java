package com.server.Dotori.exception.member.controller;

import com.server.Dotori.exception.member.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class MemberExceptionController {

    @GetMapping("/user-not-found")
    public CommonResult userNotFoundException(){
        throw new MemberNotFoundException();
    }

    @GetMapping("/user-already")
    public CommonResult userAlreadyException(){
        throw new MemberAlreadyException();
    }

    @GetMapping("/user-password-not-matching")
    public CommonResult userPasswordNotMatchingException(){
        throw new MemberPasswordNotMatchingException();
    }

    @GetMapping("/user-not-found-by-class")
    public CommonResult userNotFoundByClassException(){
        throw new MemberNotFoundByClassException();
    }

    @GetMapping("/user-authentication-answer-not-matching")
    public CommonResult userAuthenticationAnswerNotMatchingException() {
        throw new MemberAuthenticationAnswerNotMatchingException();
    }

    @GetMapping("/user-authentication-key-not-matching")
    public CommonResult userAuthenticationKeyNotMatchingException() {
        throw new MemberAuthenticationKeyNotMatchingException();
    }

    @GetMapping("/user-already-join-this-name")
    public CommonResult userAlreadyJoinThisNameException() {
        throw new MemberAlreadyJoinThisNameException();
    }

    @GetMapping("/user-already-join-this-stunum")
    public CommonResult userAlreadyJoinThisStunumException() {
        throw new MemberAlreadyJoinThisStunumException();
    }

    @GetMapping("/user-no-information")
    public CommonResult userNoInformationException() {
        throw new MemberNoInformationException();
    }

    @GetMapping("/over-certificate-time")
    public CommonResult overCertificateTimeException() {
        throw new OverCertificateTimeException();
    }

    @GetMapping("/email-has-not-been-certificate")
    public CommonResult emailHasNotBeenCertificateException() {
        throw new EmailHasNotBeenCertificateException();
    }

}
