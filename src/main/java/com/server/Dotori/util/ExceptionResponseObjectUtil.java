package com.server.Dotori.util;

import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExceptionResponseObjectUtil {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    private String getMessage(String code){
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    // *** Exception 처리시 i18n애 정의 되어있는 exception code, msg를 가져온다. ***
    public CommonResult getExceptionResponseObject(String code){
        return responseService.getFailResult(Integer.parseInt(getMessage(code + ".code")), getMessage(code + ".msg"));
    }
}
