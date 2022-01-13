package com.server.Dotori.exception.member;

import com.server.Dotori.exception.member.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface MemberExceptionHandler {

    String MEMBER_NOT_FOUND = "member-not-found";
    String MEMBER_ALREADY = "member-already";
    String MEMBER_PASSWORD_NOT_MATCHING = "member-password-not-matching";
    String MEMBER_NOT_FOUND_BY_CLASS = "member-not-found-by-class";
    String MEMBER_AUTHENTICATION_ANSWER_NOT_MATCHING = "member-authentication-answer-not-matching";
    String MEMBER_AUTHENTICATION_KEY_NOT_MATCHING = "member-authentication-key-not-matching";
    String MEMBER_ALREADY_JOIN_THIS_STUNUM = "member-already-join-this-stunum";
    String MEMBER_ALREADY_JOIN_THIS_NAME = "member-already-join-this-name";
    String MEMBER_NO_INFORMATION = "member-no-information";
    String OVER_CERTIFICATE_TIME = "over-certificate-time";
    String EMAIL_HAS_NOT_BEEN_CERTIFICATE = "email-has-not-been-certificate";

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult memberNotFoundException(MemberNotFoundException ex);

    @ExceptionHandler(MemberAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult memberAlreadyException(MemberAlreadyException ex);

    @ExceptionHandler(MemberPasswordNotMatchingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult memberPasswordNotMatchingException(MemberPasswordNotMatchingException ex);

    @ExceptionHandler(MemberNotFoundByClassException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult memberNotFoundByClassException(MemberNotFoundByClassException ex);

    @ExceptionHandler(MemberAuthenticationAnswerNotMatchingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult memberAuthenticationAnswerNotMatchingException(MemberAuthenticationAnswerNotMatchingException ex);

    @ExceptionHandler(MemberAuthenticationKeyNotMatchingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult memberAuthenticationKeyNotMatchingException(MemberAuthenticationKeyNotMatchingException ex);

    @ExceptionHandler(MemberAlreadyJoinThisNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult memberAlreadyJoinThisNameException(MemberAlreadyJoinThisNameException ex);

    @ExceptionHandler(MemberAlreadyJoinThisStunumException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult memberAlreadyJoinThisStunumException(MemberAlreadyJoinThisStunumException ex);

    @ExceptionHandler(MemberNoInformationException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult memberNoInformationException(MemberNoInformationException ex);

    @ExceptionHandler(OverCertificateTimeException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult overCertificateTimeException(OverCertificateTimeException ex);

    @ExceptionHandler(EmailHasNotBeenCertificateException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult emailHasNotBeenCertificateException(EmailHasNotBeenCertificateException ex);

}
