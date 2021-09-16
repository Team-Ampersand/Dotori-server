package com.server.Dotori.exception.customError;

import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class CustomExceptionController {

    @GetMapping
    public CommonResult exception() throws Exception {
        throw new Exception();
    }
}
