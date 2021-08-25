package com.server.Dotori.model.selfstudy.controller;

import com.server.Dotori.model.board.dto.BoardDto;
import com.server.Dotori.model.selfstudy.service.SelfStudyService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class SelfStudyController {

    private final ResponseService responseService;
    private final SelfStudyService selfStudyService;

    @GetMapping ("/selfstudy")
    @ResponseStatus( HttpStatus.CREATED )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult requestSelfStudy() {
        selfStudyService.requestSelfStudy();
        return responseService.getSuccessResult();
    }
}
