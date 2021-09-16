package com.server.Dotori.model.member.controller;

import com.server.Dotori.model.member.dto.MemberDeleteDto;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.MemberLoginDto;
import com.server.Dotori.model.member.dto.MemberPasswordDto;
import com.server.Dotori.model.member.service.email.EmailService;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import io.swagger.annotations.*;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public CommonResult signup(@RequestBody MemberDto memberDto){
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
    public CommonResult signin(@RequestBody MemberLoginDto memberLoginDto){
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
    public CommonResult passwordChange(@RequestBody MemberPasswordDto memberPasswordDto){
        Map<String,String> data = memberService.passwordChange(memberPasswordDto);
        return responseService.getSingleResult(data);
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
