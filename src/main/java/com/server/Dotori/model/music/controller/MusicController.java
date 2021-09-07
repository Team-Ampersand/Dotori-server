package com.server.Dotori.model.music.controller;

import com.server.Dotori.model.music.dto.MusicApplicationDto;
import com.server.Dotori.model.music.service.MusicService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final ResponseService responseService;

    @PostMapping("/music")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult music(@RequestBody MusicApplicationDto musicApplicationDto) {
        musicService.musicApplication(musicApplicationDto);
        return responseService.getSuccessResult();
    }

    @GetMapping("/music")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getAllMusics() {
        return responseService.getSingleResult(musicService.getAllMusic());
    }
}
