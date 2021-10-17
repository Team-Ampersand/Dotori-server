package com.server.Dotori.exception.selfstudy;

import com.server.Dotori.exception.selfstudy.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface SelfStudyExceptionHandler {

    String SELFSTUDY_CANT_APPLIED = "selfstudy-cant-applied";
    String SELFSTUDY_CANT_CHANGE = "selfstudy-cant-change";
    String SELFSTUDY_NOT_FOUND = "selfstudy-not-found";
    String SELFSTUDY_OVER_PERSONAL = "selfstudy-over-personal";
    String SELFSTUDY_CANT_CANCEL_DATE = "selfstudy-cant-cancel-date";
    String SELFSTUDY_CANT_CANCEL_TIME = "selfstudy-cant-cancel-time";
    String SELFSTUDY_CANT_REQUEST_DATE = "selfstudy-cant-request-date";
    String SELFSTUDY_CANT_REQUEST_TIME = "selfstudy-cant-request-time";


    @ExceptionHandler(SelfStudyCantApplied.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult selfStudyCantAppliedException(SelfStudyCantApplied ex);

    @ExceptionHandler(SelfStudyCantChange.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyCantChangeException(SelfStudyCantChange ex);

    @ExceptionHandler(SelfStudyNotFound.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyNotFoundException(SelfStudyNotFound ex);

    @ExceptionHandler(SelfStudyOverPersonal.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyOverPersonalException(SelfStudyOverPersonal ex);

    @ExceptionHandler(SelfStudyCantCancelDate.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyCantCancelDate(SelfStudyCantCancelDate ex);

    @ExceptionHandler(SelfStudyCantCancelTime.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyCantCancelTime(SelfStudyCantCancelTime ex);

    @ExceptionHandler(SelfStudyCantRequestDate.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyCantRequestDate(SelfStudyCantRequestDate ex);

    @ExceptionHandler(SelfStudyCantRequestTime.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult SelfStudyCantRequestTime(SelfStudyCantRequestTime ex);
}
