package com.server.Dotori.model.member.controller.Email;

import com.server.Dotori.model.member.dto.AuthPasswordDto;
import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;
import com.server.Dotori.model.member.service.email.EmailService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class EmailController {

    private final EmailService emailService;
    private final ResponseService responseService;

    /**
     * 이메일 인증 Controller
     * @param emailDto email
     * @return SuccessResult
     * @author 노경준
     */
    @PostMapping("/auth")
    public CommonResult authKey(@Valid @RequestBody EmailDto emailDto){
        emailService.authKey(emailDto);
        return responseService.getSuccessResult();
    }

    /**
     * 이메일 인증 확인 Controller
     * @param memberEmailKeyDto key
     * @return SuccessResult
     * @author 노경준
     */
    @PostMapping("/auth/check")
    public CommonResult authCheck(@Valid @RequestBody MemberEmailKeyDto memberEmailKeyDto){
        emailService.authCheck(memberEmailKeyDto);
        return responseService.getSuccessResult();
    }

    /**
     * 이메일로 임시 비밀번호을 발급해주는 Controller
     * @param authPasswordDto email, password
     * @return SuccessResult
     * @author 노경준
     */
    @PostMapping("/auth/password")
    public CommonResult authPassword(@Valid @RequestBody AuthPasswordDto authPasswordDto){
        emailService.authPassword(authPasswordDto);
        return responseService.getSuccessResult();
    }
}
