package com.server.Dotori.domain.member.controller;

import com.server.Dotori.domain.member.dto.RefreshTokenDto;
import com.server.Dotori.domain.member.service.RefreshTokenService;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.SingleResult;
import com.server.Dotori.global.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
     * @param request refreshToken, refreshRequestDto
     * @return SingResult - Map<String, String>
     * @author 노경준
     */
    @PutMapping("/refresh")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = true, dataType = "String", paramType = "header")
    })
    public SingleResult<Map<String, String>> refresh(HttpServletRequest request, @RequestBody RefreshTokenDto refreshTokenDto){
        Map<String, String> data = refreshTokenService.refreshToken(jwtTokenProvider.resolveRefreshToken(request), refreshTokenDto);
        return responseService.getSingleResult(data);
    }

}
