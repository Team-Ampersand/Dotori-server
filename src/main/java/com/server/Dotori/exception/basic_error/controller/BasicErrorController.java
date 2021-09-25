package com.server.Dotori.exception.basic_error.controller;

import com.server.Dotori.exception.basic_error.BasicErrorHandler;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BasicErrorHandler.BASIC_ERROR_BASE_URL)
@RequiredArgsConstructor
public class BasicErrorController {

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @GetMapping(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME)
    public CommonResult unauthorized(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME);
    }

    @GetMapping(BasicErrorHandler.FORBIDDEN_403_CODE_NAME)
    public CommonResult forbidden(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.FORBIDDEN_403_CODE_NAME);
    }

    @GetMapping(BasicErrorHandler.NOT_FOUND_404_CODE_NAME)
    public CommonResult notFound(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.NOT_FOUND_404_CODE_NAME);
    }
}
