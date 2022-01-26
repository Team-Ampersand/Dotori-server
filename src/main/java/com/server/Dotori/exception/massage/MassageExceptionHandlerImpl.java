package com.server.Dotori.exception.massage;

import com.server.Dotori.exception.massage.exception.*;
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
public class MassageExceptionHandlerImpl implements MassageExceptionHandler {

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult massageAlreadyException(MassageAlreadyException ex) {
        log.debug("=== Massage Already Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MASSAGE_ALREADY);
    }

    @Override
    public CommonResult massageOverException(MassageOverException ex) {
        log.debug("=== Massage Over Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MASSAGE_OVER);
    }

    @Override
    public CommonResult massageCantRequestDateException(MassageCantRequestDateException ex) {
        log.debug("=== Massage Cant Request Date Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MASSAGE_CANT_REQUEST_DATE);
    }

    @Override
    public CommonResult massageCantRequestTimeException(MassageCantRequestTimeException ex) {
        log.debug("=== Massage Cant Request Time Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MASSAGE_CANT_REQUEST_TIME);
    }

    @Override
    public CommonResult massageNotAppliedStatusException(MassageNotAppliedStatusException ex) {
        log.debug("=== Massage Not Applied Status Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MASSAGE_NOT_APPLIED_STATUS);
    }

    @Override
    public CommonResult massageNoTheresException(MassageNoTheresException ex) {
        log.debug("=== Massage No Theres Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(MASSAGE_NO_THERES);
    }
}
