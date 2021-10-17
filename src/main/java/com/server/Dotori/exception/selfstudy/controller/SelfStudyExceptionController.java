package com.server.Dotori.exception.selfstudy.controller;

import com.server.Dotori.exception.selfstudy.exception.*;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class SelfStudyExceptionController {

    @GetMapping("/selfstudy-cant-applied")
    public CommonResult selfStudyCantApplied() {
        throw new SelfStudyCantApplied();
    }

    @GetMapping("/selfstudy-cant-change")
    public CommonResult selfStudyCantChange() {
        throw new SelfStudyCantChange();
    }

    @GetMapping("/selfstudy-not-found")
    public CommonResult selfStudyNotFound() {
        throw new SelfStudyNotFound();
    }

    @GetMapping("/selfstudy-over-personal")
    public CommonResult selfStudyOverPersonal() {
        throw new SelfStudyOverPersonal();
    }

    @GetMapping("/selfstudy-cant-cancel-date")
    public CommonResult selfStudyCantCancelDate() {
        throw new SelfStudyCantCancelDate();
    }

    @GetMapping("/selfstudy-cant-cancel-time")
    public CommonResult selfStudyCantCancelTime() {
        throw new SelfStudyCantCancelTime();
    }

    @GetMapping("/selfstudy-cant-request-date")
    public CommonResult selfStudyCantRequestDate() {
        throw new SelfStudyCantRequestDate();
    }

    @GetMapping("/selfstudy-cant-request-time")
    public CommonResult selfStudyCantRequestTime() {
        throw new SelfStudyCantRequestTime();
    }

}
