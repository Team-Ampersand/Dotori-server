package com.server.Dotori.model.member.controller;

import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;
import com.server.Dotori.model.member.service.EmailService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class EmailController {

    private final EmailService emailService;
    private final ResponseService responseService;

    @PostMapping("/auth")
    public CommonResult authKey(@RequestBody EmailDto emailDto){
        emailService.authKey(emailDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/auth/check")
    public CommonResult authCheck(@RequestBody MemberEmailKeyDto memberEmailKeyDto){
        emailService.authCheck(memberEmailKeyDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/auth/password")
    public CommonResult authPassword(@RequestBody EmailDto emailDto){
        emailService.authPassword(emailDto);
        return responseService.getSuccessResult();
    }
}
