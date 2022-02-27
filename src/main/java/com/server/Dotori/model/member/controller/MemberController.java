package com.server.Dotori.model.member.controller;

import com.server.Dotori.model.member.dto.*;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

    /**
     * 비밀번호 변경 Controller
     * @param memberPasswordDto oldPassword, newPassword
     * @return SingleResult
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
     * @return SuccessResult
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
     * @return SuccessResult
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
     * @param withdrawlDto username, password
     * @return SuccessResult
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
}
