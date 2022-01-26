package com.server.Dotori.exception.massage;

import com.server.Dotori.exception.massage.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface MassageExceptionHandler {

    String MASSAGE_ALREADY = "massage-already";
    String MASSAGE_OVER = "massage-over";
    String MASSAGE_CANT_REQUEST_DATE = "massage-cant-request-date";
    String MASSAGE_CANT_REQUEST_TIME = "massage-cant-request-time";
    String MASSAGE_NOT_APPLIED_STATUS = "massage-not-applied-status";

    @ExceptionHandler(MassageAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult massageAlreadyException(MassageAlreadyException ex);

    @ExceptionHandler(MassageOverException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult massageOverException(MassageOverException ex);

    @ExceptionHandler(MassageCantRequestDateException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult massageCantRequestDateException(MassageCantRequestDateException ex);

    @ExceptionHandler(MassageCantRequestTimeException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult massageCantRequestTimeException(MassageCantRequestTimeException ex);

    @ExceptionHandler(MassageNotAppliedStatusException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult massageNotAppliedStatusException(MassageNotAppliedStatusException ex);
}
