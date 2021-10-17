package com.server.Dotori.model.member.controller;

import com.server.Dotori.model.member.dto.*;
import com.server.Dotori.model.member.service.email.EmailService;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.*;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class MemberController {

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
     * 로그인 Controller
     * @param memberLoginDto email, password
     * @return SingleResult
     * @author 노경준
     */
    @PostMapping("/signin")
    @ApiOperation(value="로그인")
    public SingleResult<Map<String, String>> signin(@Valid @RequestBody MemberLoginDto memberLoginDto){
        Map<String, String> data = memberService.signin(memberLoginDto);
        return responseService.getSingleResult(data);
    }

    /**
     * 비밀번호 변경 Controller
     * @param memberPasswordDto oldPassword, newPassword
     * @return SingleResult
     * @author 노경준
     */
    @PostMapping("/change/password")
    @ApiOperation(value="비밀번호 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult passwordChange(@Valid @RequestBody MemberPasswordDto memberPasswordDto){
        memberService.passwordChange(memberPasswordDto);
        return responseService.getSuccessResult();
    }

    /**
     * 비밀번호 찾기(재설정)를 위한 이메일로 인증번호 보내는 Controller
     * @param sendAuthKeyForChangePasswordDto email
     * @return SuccessResult
     * @author 노경준
     */
    @ApiOperation(value="비밀번호 찾기 전 이메일로 인증번호 보내기", notes = "비밀번호 찾기 전 이메일로 인증번호 보내기")
    @PostMapping("/send/change/password/authkey")
    public CommonResult sendAuthKeyForChangePassword(@Valid @RequestBody SendAuthKeyForChangePasswordDto sendAuthKeyForChangePasswordDto){
        memberService.sendAuthKeyForChangePassword(sendAuthKeyForChangePasswordDto);
        return responseService.getSuccessResult();
    }

    /**
     * 비밀번호 찾기(재설정) 전 인증번호 검증 Controller
     * @param verifiedAuthKeyAndChangePasswordDto email, key, newPassword
     * @return SuccessResult
     * @author 노경준
     */
    @ApiOperation(value="비밀번호 찾기(인증번호 검증, 비밀번호 변경)", notes = "비밀번호 찾기(인증번호 검증, 비밀번호 변경)")
    @PostMapping("/verified/auth/change/password")
    public CommonResult verifiedAuthKeyAndChangePassword(@Valid @RequestBody VerifiedAuthKeyAndChangePasswordDto verifiedAuthKeyAndChangePasswordDto){
        memberService.verifiedAuthKeyAndChangePassword(verifiedAuthKeyAndChangePasswordDto);
        return responseService.getSuccessResult();
    }

    /**
     * 로그아웃 Controller
     * @return SuccessResult
     * @author 노경준
     */
    @DeleteMapping("/logout")
    @ApiOperation(value="로그아웃")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    public CommonResult logout(){
        memberService.logout();
        return responseService.getSuccessResult();
    }

    /**
     * 회원탈퇴 Controller
     * @param memberDeleteDto username, password
     * @return SuccessResult
     * @author 노경준
     */
    @PostMapping("/delete")
    @ApiOperation(value="회원탈퇴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    public CommonResult delete(@RequestBody MemberDeleteDto memberDeleteDto){
        memberService.delete(memberDeleteDto);
        return responseService.getSuccessResult();
    }
}
