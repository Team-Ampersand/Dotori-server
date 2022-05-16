package com.server.Dotori.domain.member.controller;

import com.server.Dotori.domain.member.dto.*;
import com.server.Dotori.domain.member.service.MemberService;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    /**
     * 비밀번호 변경 Controller
     * @param memberPasswordDto oldPassword, newPassword
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @PutMapping("/password")
    @ApiOperation(value="비밀번호 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult changePassword(@Valid @RequestBody ChangePasswordDto memberPasswordDto){
        memberService.changePassword(memberPasswordDto);
        return responseService.getSuccessResult();
    }

    /**
     * 비밀번호 찾기(변경) 전 이메일 인증 Controller
     * @param emailDto email
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @PostMapping("/password/email")
    @ApiOperation(value="비밀번호 찾기(변경) 전 이메일 인증")
    public CommonResult sendEmailChangePassword(@Valid @RequestBody EmailDto emailDto){
        memberService.sendEmailChangePassword(emailDto);
        return responseService.getSuccessResult();
    }

    /**
     * 비밀번호 찾기(변경) 전 이메일 인증 확인 Controller
     * @param changePasswordEmailCheckDto email, key, newPassword
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @PostMapping("/password/email/check")
    @ApiOperation(value="비밀번호 찾기(변경) 전 이메일 인증 확인")
    public CommonResult checkEmailChangePassword(@Valid @RequestBody ChangePasswordEmailCheckDto changePasswordEmailCheckDto){
        memberService.checkEmailChangePassword(changePasswordEmailCheckDto);
        return responseService.getSuccessResult();
    }

    /**
     * 로그아웃 Controller
     * @return CommonResult - SuccessResult
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
     * @param withdrawlDto username, password
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @PostMapping("/withdrawal")
    @ApiOperation(value="회원탈퇴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    public CommonResult withdrawal(@RequestBody WithdrawlDto withdrawlDto){
        memberService.withdrawal(withdrawlDto);
        return responseService.getSuccessResult();
    }

    /**
     * 성별변경 Controller
     * @param setGenderDto email, gender
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @PutMapping("/gender")
    @ApiOperation(value="성별설정")
    public CommonResult setGender(@RequestBody SetGenderDto setGenderDto){
        memberService.setGender(setGenderDto);
        return responseService.getSuccessResult();
    }
}
