package com.server.Dotori.exception.selfstudy;

import com.server.Dotori.exception.selfstudy.exception.SelfStudyCantApplied;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyCantChange;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyNotFound;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyOverPersonal;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor @Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class SelfStudyExceptionHandlerImpl implements SelfStudyExceptionHandler {

    private ExceptionResponseObjectUtil exceptionResponseObjectUtil;

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
}
