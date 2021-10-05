package com.server.Dotori.exception.user;

import com.server.Dotori.exception.user.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserExceptionHandler {

    String USER_NOT_FOUND = "user-not-found";
    String USER_ALREADY = "user-already";
    String USER_PASSWORD_NOT_MATCHING = "user-password-not-matching";
    String USER_NOT_FOUND_BY_CLASS = "user-not-found-by-class";
    String USER_AUTHENTICATION_ANSWER_NOT_MATCHING = "user-authentication-answer-not-matching";
    String USER_AUTHENTICATION_KEY_NOT_MATCHING = "user-authentication-key-not-matching"
    String USER_ALREADY_JOIN_THIS_STUNUM = "user-already-join-this-stunum";
    String USER_ALREADY_JOIN_THIS_NAME = "user-already-join-this-name";

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult userNotFoundException(UserNotFoundException ex);

    @ExceptionHandler(UserAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult userAlreadyException(UserAlreadyException ex);

    @ExceptionHandler(UserPasswordNotMatchingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult userPasswordNotMatchingException(UserPasswordNotMatchingException ex);

    @ExceptionHandler(UserNotFoundByClassException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult userNotFoundByClassException(UserNotFoundByClassException ex);

    @ExceptionHandler(UserAuthenticationAnswerNotMatchingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult userAuthenticationAnswerNotMatchingException(UserAuthenticationAnswerNotMatchingException ex);

    @ExceptionHandler(UserAuthenticationKeyNotMatchingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult userAuthenticationKeyNotMatchingException(UserAuthenticationKeyNotMatchingException ex);

    @ExceptionHandler(UserAlreadyJoinThisNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult userAlreadyJoinThisNameException(UserAlreadyJoinThisNameException ex);

    @ExceptionHandler(UserAlreadyJoinThisStunumException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult userAlreadyJoinThisStunumException(UserAlreadyJoinThisStunumException ex);

}
