package com.server.Dotori.model.member.controller;

import com.server.Dotori.model.member.service.RefreshTokenService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    private final ResponseService responseService;

    @GetMapping("/refresh")
    public CommonResult refresh(HttpServletRequest request){
        refreshTokenService.getRefreshToken(request);
        return responseService.getSuccessResult();
    }

}
