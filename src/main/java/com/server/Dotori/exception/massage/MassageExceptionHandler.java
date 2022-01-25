package com.server.Dotori.exception.massage;

import com.server.Dotori.exception.massage.exception.MassageAlreadyException;
import com.server.Dotori.exception.massage.exception.MassageOverException;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface MassageHandler {

    String MASSAGE_ALREADY = "massage-already";
    String MASSAGE_OVER = "massage-over";

    @ExceptionHandler(MassageAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult massageAlreadyException(MassageAlreadyException ex);

    @ExceptionHandler(MassageOverException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CommonResult massageOverException(MassageOverException ex);
}
