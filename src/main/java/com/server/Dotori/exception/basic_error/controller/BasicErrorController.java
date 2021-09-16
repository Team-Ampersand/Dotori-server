package com.server.Dotori.exception.customError.controller;

import com.server.Dotori.exception.customError.BasicErrorHandler;
import com.server.Dotori.exception.customError.exception.CustomForbiddenException;
import com.server.Dotori.exception.customError.exception.CustomNotFoundException;
import com.server.Dotori.exception.customError.exception.CustomUnauthorizedException;
import com.server.Dotori.exception.response.CustomException;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BasicErrorHandler.BASIC_ERROR_BASE_URL)
public class BasicErrorController implements ErrorController {

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
