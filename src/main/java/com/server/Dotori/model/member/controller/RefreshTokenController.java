package com.server.Dotori.model.member.controller;

import com.server.Dotori.model.member.service.RefreshTokenService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    private final ResponseService responseService;

    /**
     * 토큰 재발급 Controller
     * @param request accessToken, refreshToken
     * @return SuccessResult
     * @author 노경준
     */
    @GetMapping("/refresh")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult refresh(HttpServletRequest request){
        refreshTokenService.getRefreshToken(request);
        return responseService.getSuccessResult();
    }

}
