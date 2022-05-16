package com.server.Dotori.domain.member.controller;

import com.server.Dotori.domain.member.dto.*;
import com.server.Dotori.domain.member.service.MemberService;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.CommonResult;
import com.server.Dotori.global.response.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SignController {

    private final MemberService memberService;
    private final ResponseService responseService;

    /**
     * 회원가입 Controller
     * @param memberDto username, stdNum, password, email, answer
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @PostMapping("/signup")
    @ApiOperation(value="회원가입")
    public CommonResult signup(@Valid @RequestBody MemberDto memberDto){
        memberService.signup(memberDto);
        return responseService.getSuccessResult();
    }

    /**
     * 회원가입 이메일 인증 Controller
     * @param emailDto email
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @PostMapping("/signup/email")
    @ApiOperation(value="회원가입 이메일 인증")
    public CommonResult sendEmailSignup(@Valid @RequestBody EmailDto emailDto){
        memberService.sendEmailSignup(emailDto);
        return responseService.getSuccessResult();
    }

    /**
     * 회원가입 이메일 인증 확인 Controller
     * @param memberEmailKeyDto key
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @PostMapping("/signup/email/check")
    @ApiOperation(value="회원가입 이메일 인증 확인")
    public CommonResult checkEmailSignup(@Valid @RequestBody SignUpEmailCheckDto memberEmailKeyDto){
        memberService.checkEmailSignup(memberEmailKeyDto);
        return responseService.getSuccessResult();
    }

    /**
     * 로그인 Controller
     * @param memberLoginDto email, password
     * @return SingleResult - List<SignInResponseDto>
     * @author 노경준
     */
    @PostMapping("/signin")
    @ApiOperation(value="로그인")
    public SingleResult<SignInResponseDto> signin(@Valid @RequestBody SignInDto memberLoginDto){
        SignInResponseDto data = memberService.signIn(memberLoginDto);
        return responseService.getSingleResult(data);
    }
}
