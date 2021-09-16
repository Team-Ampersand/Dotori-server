package com.server.Dotori.model.music.controller.developer;

import com.server.Dotori.model.music.dto.MusicApplicationDto;
import com.server.Dotori.model.music.dto.MusicResDto;
import com.server.Dotori.model.music.service.MusicService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/developer")
@RequiredArgsConstructor
public class DeveloperMusicController {

    private final ResponseService responseService;
    private final MusicService musicService;

    /**
     * 음악 신청 컨트롤러
     * @param musicApplicationDto (url)
     * @return CommonResult - SuccessResult
     */
    @PostMapping("/music")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "음악 신청", notes = "음악 신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult music(@RequestBody MusicApplicationDto musicApplicationDto) {
        musicService.musicApplication(musicApplicationDto);
        return responseService.getSuccessResult();
    }

    /**
     * 음악 신청목록 조회 컨트롤러
     * @return SingleResult - List - MusicResDto
     */
    @GetMapping("/music")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "음악 신청목록 조회", notes = "음악 신청목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<List<MusicResDto>> getAllMusics() {
        return responseService.getSingleResult(musicService.getAllMusic());
    }

    /**
     * 음악신청 목록 개별 삭제 컨트롤러
     * @param id id
     * @return CommonResult - SuccessResult
     */
    @DeleteMapping("/music/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT )
    @ApiOperation(value = "음악 신청목록 개별 삭제", notes = "음악 신청목록 개별 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult deleteMusic(@PathVariable("id") Long id) {
        musicService.deleteMusic(id);
        return responseService.getSuccessResult();
    }
}
