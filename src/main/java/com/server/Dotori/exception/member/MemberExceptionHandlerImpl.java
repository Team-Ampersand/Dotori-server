package com.server.Dotori.exception.member;

import com.server.Dotori.exception.member.exception.*;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@RestControllerAdvice
public class MemberExceptionHandlerImpl implements MemberExceptionHandler {

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult memberNotFoundException(MemberNotFoundException ex) {
        log.debug("=== Member NotFound Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_NOT_FOUND);
    }

    @Override
    public CommonResult memberAlreadyException(MemberAlreadyException ex) {
        log.debug("=== Member Already Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_ALREADY);
    }

    @Override
    public CommonResult memberPasswordNotMatchingException(MemberPasswordNotMatchingException ex) {
        log.debug("=== Member Password Not Matching Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_PASSWORD_NOT_MATCHING);
    }

    @Override
    public CommonResult memberNotFoundByClassException(MemberNotFoundByClassException ex) {
        log.debug("=== Member NotFound By Class Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_NOT_FOUND_BY_CLASS);
    }

    @Override
    public CommonResult memberAuthenticationAnswerNotMatchingException(MemberAuthenticationAnswerNotMatchingException ex) {
        log.debug("=== Member Authentication Answer Not Matching Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_AUTHENTICATION_ANSWER_NOT_MATCHING);
    }

    @Override
    public CommonResult memberAuthenticationKeyNotMatchingException(MemberAuthenticationKeyNotMatchingException ex) {
        log.debug("=== Member Authentication Key Not Matching Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_AUTHENTICATION_KEY_NOT_MATCHING);
    }

    @Override
    public CommonResult memberAlreadyJoinThisNameException(MemberAlreadyJoinThisNameException ex) {
        log.debug("=== Member Already Join This Name Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_ALREADY_JOIN_THIS_NAME);
    }

    @Override
    public CommonResult memberAlreadyJoinThisStunumException(MemberAlreadyJoinThisStunumException ex) {
        log.debug("=== Member Already Join This Stunum Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_ALREADY_JOIN_THIS_STUNUM);
    }

    @Override
    public CommonResult memberNoInformationException(MemberNoInformationException ex) {
        log.debug("=== Member No Information Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MEMBER_NO_INFORMATION);
    }

    @Override
    public CommonResult overCertificateTimeException(OverCertificateTimeException ex) {
        log.debug("=== Over Certificate Time Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(OVER_CERTIFICATE_TIME);
    }

    @Override
    public CommonResult emailHasNotBeenCertificateException(EmailHasNotBeenCertificateException ex) {
        log.debug("=== Email Has Not Been Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(EMAIL_HAS_NOT_BEEN_CERTIFICATE);
    }

}
