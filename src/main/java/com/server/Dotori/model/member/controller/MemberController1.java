package com.server.Dotori.model.member.controller;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.MemberLoginDto;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class MemberController1 {

    private final MemberService memberService;
    private final ResponseService responseService;

    @PostMapping("/signup")
    public CommonResult signup(@RequestBody MemberDto memberDto){
        memberService.signup(memberDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/signin")
    public CommonResult signin(@RequestBody MemberLoginDto memberLoginDto){
        Map<String, String> data = memberService.signin(memberLoginDto);
        return responseService.getSingleResult(data);
    }


}
