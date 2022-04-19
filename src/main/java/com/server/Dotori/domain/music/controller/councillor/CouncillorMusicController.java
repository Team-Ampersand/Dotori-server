package com.server.Dotori.domain.music.controller.councillor;

import com.server.Dotori.domain.music.dto.MusicApplicationDto;
import com.server.Dotori.domain.music.dto.MusicResDto;
import com.server.Dotori.domain.music.service.MusicService;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.CommonResult;
import com.server.Dotori.global.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * @since 1.0.0
 * @author 배태현
 */
@RestController
@RequestMapping("/v1/councillor")
@RequiredArgsConstructor
public class CouncillorMusicController {

    private final ResponseService responseService;
    private final MusicService musicService;

    /**
     * 음악 신청 컨트롤러
     * @param musicApplicationDto (url)
     * @return CommonResult - SuccessResult
     */
    @PostMapping("/music")
    @ResponseStatus( HttpStatus.CREATED )
    @ApiOperation(value = "음악 신청", notes = "음악 신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult musicCouncillor(@Valid @RequestBody MusicApplicationDto musicApplicationDto) {
        musicService.musicApplication(musicApplicationDto, LocalDateTime.now().getDayOfWeek());
        return responseService.getSuccessResult();
    }

    /**
     * 음악 신청목록 조회 컨트롤러 (쿼리스트링으로 날짜검색 조건 설정)
     * @return SingleResult - List - MusicResDto
     */
    @GetMapping("/music")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "음악 신청목록 조회", notes = "음악 신청목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<List<MusicResDto>> getMusicListByDateCouncillor(@RequestParam(value = "date", required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        return responseService.getSingleResult(musicService.getMusicListByDate(date));
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
    public CommonResult deleteMusicCouncillor(@PathVariable("id") Long id) {
        musicService.deleteMusic(id);
        return responseService.getSuccessResult();
    }
}
