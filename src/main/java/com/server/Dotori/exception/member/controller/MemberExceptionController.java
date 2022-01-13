package com.server.Dotori.exception.user.controller;

import com.server.Dotori.exception.user.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class UserExceptionController {

    @GetMapping("/user-not-found")
    public CommonResult userNotFoundException(){
        throw new UserNotFoundException();
    }

    @GetMapping("/user-already")
    public CommonResult userAlreadyException(){
        throw new UserAlreadyException();
    }

    @GetMapping("/user-password-not-matching")
    public CommonResult userPasswordNotMatchingException(){
        throw new UserPasswordNotMatchingException();
    }

    @GetMapping("/user-not-found-by-class")
    public CommonResult userNotFoundByClassException(){
        throw new UserNotFoundByClassException();
    }

    @GetMapping("/user-authentication-answer-not-matching")
    public CommonResult userAuthenticationAnswerNotMatchingException() {
        throw new UserAuthenticationAnswerNotMatchingException();
    }

    @GetMapping("/user-authentication-key-not-matching")
    public CommonResult userAuthenticationKeyNotMatchingException() {
        throw new UserAuthenticationKeyNotMatchingException();
    }

    @GetMapping("/user-already-join-this-name")
    public CommonResult userAlreadyJoinThisNameException() {
        throw new UserAlreadyJoinThisNameException();
    }

    @GetMapping("/user-already-join-this-stunum")
    public CommonResult userAlreadyJoinThisStunumException() {
        throw new UserAlreadyJoinThisStunumException();
    }

    @GetMapping("/user-no-information")
    public CommonResult userNoInformationException() {
        throw new UserNoInformationException();
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
