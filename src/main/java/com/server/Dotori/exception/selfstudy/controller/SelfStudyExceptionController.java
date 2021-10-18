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
        throw new SelfStudyCantAppliedException();
    }

    @GetMapping("/selfstudy-cant-change")
    public CommonResult selfStudyCantChange() {
        throw new SelfStudyCantChangeException();
    }

    @GetMapping("/selfstudy-not-found")
    public CommonResult selfStudyNotFound() {
        throw new SelfStudyNotFoundException();
    }

    @GetMapping("/selfstudy-over-personal")
    public CommonResult selfStudyOverPersonal() {
        throw new SelfStudyOverPersonalException();
    }

    @GetMapping("/selfstudy-cant-cancel-date")
    public CommonResult selfStudyCantCancelDate() {
        throw new SelfStudyCantCancelDateException();
    }

    @GetMapping("/selfstudy-cant-cancel-time")
    public CommonResult selfStudyCantCancelTime() {
        throw new SelfStudyCantCancelTimeException();
    }

    @GetMapping("/selfstudy-cant-request-date")
    public CommonResult selfStudyCantRequestDate() {
        throw new SelfStudyCantRequestDateException();
    }

    @GetMapping("/selfstudy-cant-request-time")
    public CommonResult selfStudyCantRequestTime() {
        throw new SelfStudyCantRequestTimeException();
    }

}
