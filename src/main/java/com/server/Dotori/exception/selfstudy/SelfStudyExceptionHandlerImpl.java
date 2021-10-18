package com.server.Dotori.exception.selfstudy;

import com.server.Dotori.exception.selfstudy.exception.*;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SelfStudyExceptionHandlerImpl implements SelfStudyExceptionHandler {

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult selfStudyNotFoundException(SelfStudyNotFound ex) {
        log.debug("=== SelfStudy NotFound Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(SELFSTUDY_NOT_FOUND);
    }

    @Override
    public CommonResult selfStudyCantChangeException(SelfStudyCantChange ex) {
        log.debug("=== SelfStudy Cant Change Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(SELFSTUDY_CANT_CHANGE);
    }

    @Override
    public CommonResult selfStudyCantAppliedException(SelfStudyCantApplied ex) {
        log.debug("=== SelfStudy Cant Applied Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(SELFSTUDY_CANT_APPLIED);
    }

    @Override
    public CommonResult selfStudyOverPersonalException(SelfStudyOverPersonal ex) {
        log.debug("=== SelfStudy Over Personal Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(SELFSTUDY_OVER_PERSONAL);
    }

    @Override
    public CommonResult selfStudyCantCancelDate(SelfStudyCantCancelDate ex) {
        log.debug("=== SelfStudy Cant Cancel Date Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(SELFSTUDY_CANT_CANCEL_DATE);
    }

    @Override
    public CommonResult selfStudyCantCancelTime(SelfStudyCantCancelTime ex) {
        log.debug("=== SelfStudy Cant Cancel Time Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(SELFSTUDY_CANT_CANCEL_TIME);
    }

    @Override
    public CommonResult selfStudyCantRequestDate(SelfStudyCantRequestDate ex) {
        log.debug("=== SelfStudy Cant Request Date Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(SELFSTUDY_CANT_REQUEST_DATE);
    }

    @Override
    public CommonResult SelfStudyCantRequestTime(SelfStudyCantRequestTime ex) {
        log.debug("=== SelfStudy Cant Request Time Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(SELFSTUDY_CANT_REQUEST_TIME);
    }
}
