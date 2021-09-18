package com.server.Dotori.exception.selfstudy.controller;

import com.server.Dotori.exception.selfstudy.exception.SelfStudyCantApplied;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyCantChange;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyNotFound;
import com.server.Dotori.exception.selfstudy.exception.SelfStudyOverPersonal;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class SelfStudyExceptionController {

    @GetMapping("/selfstudy-already")
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
}
