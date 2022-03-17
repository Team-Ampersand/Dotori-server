//package com.server.Dotori.exception.dto;
//
//import com.server.Dotori.response.ResponseService;
//import com.server.Dotori.response.result.CommonResult;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@Slf4j
//@RestControllerAdvice @Order(Ordered.HIGHEST_PRECEDENCE)
//@RequiredArgsConstructor
//public class InvalidRequestDataHandler {
//
//    private final ResponseService responseService;
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    private CommonResult invalidRequestValueHandler(MethodArgumentNotValidException ex){
//        log.debug("=== MethodArgumentNotValidException 발생(사용자가 서버에서 요구사항에 맞지 않은 데이터를 요청함) ===");
//        BindingResult bindingResult = ex.getBindingResult();
//
//        StringBuilder errorResult = new StringBuilder();
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            errorResult.append(fieldError.getField()).append(":");
//            errorResult.append(fieldError.getDefaultMessage());
//            errorResult.append(".,");
//        }
//        errorResult.deleteCharAt(errorResult.lastIndexOf(".,")); // 마지막 글자에서 ".,"글자 제거
//
//        return responseService.getFailResult(-10, errorResult.toString());
//    }
//}
