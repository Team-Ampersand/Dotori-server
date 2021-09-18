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

    @GetMapping("/user-authentication-number-not-matching")
    public CommonResult userAuthenticationNumberNotMatchingException() {
        throw new UserAuthenticationNumberNotMatchingException();
    }

    @GetMapping("/user-already-join-this-name")
    public CommonResult userAlreadyJoinThisNameException() {
        throw new UserAlreadyJoinThisNameException();
    }

    @GetMapping("/user-already-join-this-stunum")
    public CommonResult userAlreadyJoinThisStunumException() {
        throw new UserAlreadyJoinThisStunumException();
    }

}
