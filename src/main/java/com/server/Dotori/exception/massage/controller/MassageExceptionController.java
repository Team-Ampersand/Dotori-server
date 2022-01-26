package com.server.Dotori.exception.massage.controller;

import com.server.Dotori.exception.massage.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class MassageExceptionController {

    @GetMapping("/massage-already")
    public CommonResult massageAlreadyException() {
        throw new MassageAlreadyException();
    }

    @GetMapping("/massage-over")
    public CommonResult massageOverException() {
        throw new MassageOverException();
    }

    @GetMapping("/massage-cant-request-date")
    public CommonResult massageCantRequestDateException() {
        throw new MassageCantRequestDateException();
    }

    @GetMapping("/massage-cant-request-time")
    public CommonResult massageCantRequestTimeException() {
        throw new MassageCantRequestTimeException();
    }

    @GetMapping("/massage-not-applied-status")
    public CommonResult massageNotAppliedStatusException() {
        throw new MassageNotAppliedStatusException();
    }

}
