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
    String USER_AUTHENTICATION_NUMBER_NOT_MATCHING = "user-authentication-number-not-matching";

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult userNotFoundException(UserNotFoundException ex);

    @ExceptionHandler(UserAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult userAlreadyException(UserAlreadyException ex);

    @ExceptionHandler(UserPasswordNotMatchingException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult userPasswordNotMatchingException(UserPasswordNotMatchingException ex);

    @ExceptionHandler(UserNotFoundByClassException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult userNotFoundByClassException(UserNotFoundByClassException ex);

    @ExceptionHandler(UserAuthenticationNumberNotMatchingException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    CommonResult userAuthenticationNumberNotMatchingException(UserAuthenticationNumberNotMatchingException ex);
}
