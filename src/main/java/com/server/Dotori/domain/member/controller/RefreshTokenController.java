package com.server.Dotori.domain.member.controller;

import com.server.Dotori.domain.member.service.RefreshTokenService;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.SingleResult;
import com.server.Dotori.global.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 토큰 재발급 Controller
     * @param request refreshToken
     * @return SingleResult
     * @author 노경준, 배태현
     */
    @PutMapping("/refresh")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = true, dataType = "String", paramType = "header")
    })
    public SingleResult<Map<String, String>> refresh(HttpServletRequest request){
        Map<String, String> data = refreshTokenService.getRefreshToken(jwtTokenProvider.resolveRefreshToken(request));
        return responseService.getSingleResult(data);
    }

}
