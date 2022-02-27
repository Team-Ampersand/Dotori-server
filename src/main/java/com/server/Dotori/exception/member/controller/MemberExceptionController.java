package com.server.Dotori.exception.member.controller;

import com.server.Dotori.exception.member.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class MemberExceptionController {

    @GetMapping("/member-not-found")
    public CommonResult memberNotFoundException(){
        throw new MemberNotFoundException();
    }

    @GetMapping("/member-already")
    public CommonResult memberAlreadyException(){
        throw new MemberAlreadyException();
    }

    @GetMapping("/member-password-not-matching")
    public CommonResult memberPasswordNotMatchingException(){
        throw new MemberPasswordNotMatchingException();
    }

    @GetMapping("/member-not-found-by-class")
    public CommonResult memberNotFoundByClassException(){
        throw new MemberNotFoundByClassException();
    }

    @GetMapping("/member-authentication-answer-not-matching")
    public CommonResult memberAuthenticationAnswerNotMatchingException() {
        throw new MemberAuthenticationAnswerNotMatchingException();
    }

    @GetMapping("/member-authentication-key-not-matching")
    public CommonResult memberAuthenticationKeyNotMatchingException() {
        throw new MemberAuthenticationKeyNotMatchingException();
    }

    @GetMapping("/member-already-join-this-name")
    public CommonResult memberAlreadyJoinThisNameException() {
        throw new MemberAlreadyJoinThisNameException();
    }

    @GetMapping("/member-already-join-this-stunum")
    public CommonResult memberAlreadyJoinThisStunumException() {
        throw new MemberAlreadyJoinThisStunumException();
    }

    @GetMapping("/member-no-information")
    public CommonResult memberNoInformationException() {
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
