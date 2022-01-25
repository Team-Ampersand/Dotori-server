package com.server.Dotori.model.member.controller;

import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.SignInDto;
import com.server.Dotori.model.member.dto.SignUpEmailCheckDto;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SignController {

    private final MemberService memberService;
    private final ResponseService responseService;

    /**
     * 회원가입 Controller
     * @param memberDto username, stdNum, password, email, answer
     * @return SuccessResult
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
     * @return SuccessResult
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
     * @return SuccessResult
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
     * @return SingleResult
     * @author 노경준
     */
    @PostMapping("/signin")
    @ApiOperation(value="로그인")
    public SingleResult<Map<String, String>> signin(@Valid @RequestBody SignInDto memberLoginDto){
        Map<String, String> data = memberService.signIn(memberLoginDto);
        return responseService.getSingleResult(data);
    }
}
