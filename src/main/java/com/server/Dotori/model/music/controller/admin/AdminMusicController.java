package com.server.Dotori.model.music.controller.admin;

import com.server.Dotori.model.music.dto.DateMusicDto;
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

/**
 * @since 1.0.0
 * @author 배태현
 */
@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminMusicController {

    private final ResponseService responseService;
    private final MusicService musicService;

    /**
     * 음악 신청목록 조회 컨트롤러
     * @return SingleResult - List - MusicResDto
     */
    @GetMapping("/music")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "음악 신청목록 전체 조회", notes = "음악 신청목록 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<List<MusicResDto>> getAllMusicsAdmin() {
        return responseService.getSingleResult(musicService.getAllMusic());
    }

    /**
     * 음악신청 목록 개별 삭제 컨트롤러
     * @param id id
     * @return CommonResult - SuccessResult
     */
    @DeleteMapping("/music/{id}")
    @ResponseStatus (HttpStatus.OK )
    @ApiOperation(value = "음악 신청목록 개별 삭제", notes = "음악 신청목록 개별 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult deleteMusicAdmin(@PathVariable("id") Long id) {
        musicService.deleteMusic(id);
        return responseService.getSuccessResult();
    }

    /**
     * 오늘 신청된 음악목록을 조회하는 컨트롤러
     * @return SingleResult - List - MusicResDto
     * @author 배태현
     */
    @GetMapping("/music/current")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "오늘 신청된 음악목록 조회", notes = "오늘 신청된 음악목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<List<MusicResDto>> findCurrentDateMusicAdmin() {
        return responseService.getSingleResult(musicService.getCurrentDateMusic());
    }

    /**
     * 특정 날짜에 신청된 음악목록을 조회하는 컨트롤러
     * @param dateMusicDto date
     * @return SingleResult - List - MusicResDto
     * @author 배태현
     */
    @PostMapping("/music/date")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "해당 날짜에 신청된 음악목록 조회", notes = "해당 날짜에 신청된 음악목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<List<MusicResDto>> findDateMusicAdmin(@RequestBody DateMusicDto dateMusicDto) {
        return responseService.getSingleResult(musicService.getDateMusic(dateMusicDto.getDate()));
    }
}