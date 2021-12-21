package com.server.Dotori.exception.user;

import com.server.Dotori.exception.user.exception.*;
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
public class UserExceptionHandlerImpl implements UserExceptionHandler {

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult userNotFoundException(UserNotFoundException ex) {
        log.debug("=== User NotFound Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_NOT_FOUND);
    }

    @Override
    public CommonResult userAlreadyException(UserAlreadyException ex) {
        log.debug("=== User Already Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_ALREADY);
    }

    @Override
    public CommonResult userPasswordNotMatchingException(UserPasswordNotMatchingException ex) {
        log.debug("=== User Password Not Matching Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_PASSWORD_NOT_MATCHING);
    }

    @Override
    public CommonResult userNotFoundByClassException(UserNotFoundByClassException ex) {
        log.debug("=== User NotFound By Class Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_NOT_FOUND_BY_CLASS);
    }

    @Override
    public CommonResult userAuthenticationAnswerNotMatchingException(UserAuthenticationAnswerNotMatchingException ex) {
        log.debug("=== User Authentication Answer Not Matching Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_AUTHENTICATION_ANSWER_NOT_MATCHING);
    }

    @Override
    public CommonResult userAuthenticationKeyNotMatchingException(UserAuthenticationKeyNotMatchingException ex) {
        log.debug("=== User Authentication Key Not Matching Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_AUTHENTICATION_KEY_NOT_MATCHING);
    }

    @Override
    public CommonResult userAlreadyJoinThisNameException(UserAlreadyJoinThisNameException ex) {
        log.debug("=== User Already Join This Name Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_ALREADY_JOIN_THIS_NAME);
    }

    @Override
    public CommonResult userAlreadyJoinThisStunumException(UserAlreadyJoinThisStunumException ex) {
        log.debug("=== User Already Join This Stunum Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_ALREADY_JOIN_THIS_STUNUM);
    }

    @Override
    public CommonResult userNoInformationException(UserNoInformationException ex) {
        log.debug("=== User No Information Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(USER_NO_INFORMATION);
    }

    @Override
    public CommonResult overCertificateTimeException(OverCertificateTimeException ex) {
        log.debug("=== Over Certificate Time Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(OVER_CERTIFICATE_TIME);
    }

    @Override
    public CommonResult emailHasNotBeenCertificateException(EmailHasNotBeenCertificate ex) {
        log.debug("=== Email Has Not Been Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(EMAIL_HAS_NOT_BEEN_CERTIFICATE);
    }

}
