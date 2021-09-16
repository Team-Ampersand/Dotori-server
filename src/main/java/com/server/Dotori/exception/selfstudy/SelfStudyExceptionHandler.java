package com.server.Dotori.exception.selfstudy;

import com.server.Dotori.exception.selfstudy.exception.SelfStudyCantApplied;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyCantChange;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyNotFound;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyOverPersonal;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface SelfStudyExceptionHandler {

    String SELFSTUDY_CANT_APPLIED = "selfstudy-cant-applied";
    String SELFSTUDY_CANT_CHANGE = "selfstudy-cant-change";
    String SELFSTUDY_NOT_FOUND = "selfstudy-not-found";
    String SELFSTUDY_OVER_PERSONAL = "selfstudy-over-personal";

    @ExceptionHandler(SelfStudyCantApplied.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult selfStudyCantAppliedException(SelfStudyCantApplied ex);

    @ExceptionHandler(SelfStudyCantChange.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    CommonResult selfStudyCantChangeException(SelfStudyCantChange ex);

    @ExceptionHandler(SelfStudyNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    CommonResult selfStudyNotFoundException(SelfStudyNotFound ex);

    @ExceptionHandler(SelfStudyOverPersonal.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    CommonResult selfStudyOverPersonalException(SelfStudyOverPersonal ex);
}
