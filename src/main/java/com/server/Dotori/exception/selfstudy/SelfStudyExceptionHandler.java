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


    @ExceptionHandler(SelfStudyCantAppliedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult selfStudyCantAppliedException(SelfStudyCantAppliedException ex);

    @ExceptionHandler(SelfStudyCantChangeException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyCantChangeException(SelfStudyCantChangeException ex);

    @ExceptionHandler(SelfStudyNotFoundException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyNotFoundException(SelfStudyNotFoundException ex);

    @ExceptionHandler(SelfStudyOverPersonalException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyOverPersonalException(SelfStudyOverPersonalException ex);

    @ExceptionHandler(SelfStudyCantCancelDateException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyCantCancelDateException(SelfStudyCantCancelDateException ex);

    @ExceptionHandler(SelfStudyCantCancelTimeException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyCantCancelTimeException(SelfStudyCantCancelTimeException ex);

    @ExceptionHandler(SelfStudyCantRequestDateException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult selfStudyCantRequestDateException(SelfStudyCantRequestDateException ex);

    @ExceptionHandler(SelfStudyCantRequestTimeException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult SelfStudyCantRequestTimeException(SelfStudyCantRequestTimeException ex);
}
