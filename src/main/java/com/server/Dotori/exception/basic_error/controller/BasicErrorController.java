package com.server.Dotori.exception.basic_error.controller;

import com.server.Dotori.exception.basic_error.BasicErrorHandler;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BasicErrorHandler.BASIC_ERROR_BASE_URL)
@RequiredArgsConstructor
public class BasicErrorController {

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    // *** GET *** //
    @GetMapping(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME)
    public CommonResult unauthorizedGet(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME);
    }

    @GetMapping(BasicErrorHandler.FORBIDDEN_403_CODE_NAME)
    public CommonResult forbiddenGet(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.FORBIDDEN_403_CODE_NAME);
    }

    @GetMapping(BasicErrorHandler.NOT_FOUND_404_CODE_NAME)
    public CommonResult notFoundGet(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.NOT_FOUND_404_CODE_NAME);
    }


    // *** POST *** //
    @PostMapping(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME)
    public CommonResult unauthorizedPost(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME);
    }

    @PostMapping(BasicErrorHandler.FORBIDDEN_403_CODE_NAME)
    public CommonResult forbiddenPost(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.FORBIDDEN_403_CODE_NAME);
    }

    @PostMapping(BasicErrorHandler.NOT_FOUND_404_CODE_NAME)
    public CommonResult notFoundPost(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.NOT_FOUND_404_CODE_NAME);
    }


    // *** PUT *** //
    @PutMapping(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME)
    public CommonResult unauthorizedPut(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME);
    }

    @PutMapping(BasicErrorHandler.FORBIDDEN_403_CODE_NAME)
    public CommonResult forbiddenPut(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.FORBIDDEN_403_CODE_NAME);
    }

    @PutMapping(BasicErrorHandler.NOT_FOUND_404_CODE_NAME)
    public CommonResult notFoundPut(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.NOT_FOUND_404_CODE_NAME);
    }


    // *** DELETE *** //
    @DeleteMapping(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME)
    public CommonResult unauthorizedDelete(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.UNAUTHORIZED_401_CODE_NAME);
    }

    @DeleteMapping(BasicErrorHandler.FORBIDDEN_403_CODE_NAME)
    public CommonResult forbiddenDelete(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.FORBIDDEN_403_CODE_NAME);
    }

    @DeleteMapping(BasicErrorHandler.NOT_FOUND_404_CODE_NAME)
    public CommonResult notFoundDelete(){
        return exceptionResponseObjectUtil.getExceptionResponseObject(BasicErrorHandler.NOT_FOUND_404_CODE_NAME);
    }
}
