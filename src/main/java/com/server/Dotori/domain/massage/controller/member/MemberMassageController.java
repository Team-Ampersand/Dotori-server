package com.server.Dotori.domain.massage.controller.member;

import com.server.Dotori.domain.massage.service.MassageService;
import com.server.Dotori.domain.massage.dto.MassageStudentsDto;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.CommonResult;
import com.server.Dotori.global.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberMassageController {

    private final MassageService massageService;
    private final ResponseService responseService;

    /**
     * 안마의자 신청
     * @return CommonResult - SuccessResult
     */
    @PutMapping("/massage")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자 신청", notes = "안마의자 신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult requestMassageMember() {
        LocalDateTime currentTime = LocalDateTime.now();
        massageService.requestMassage(currentTime.getDayOfWeek(), currentTime.getHour(), currentTime.getMinute());
        return responseService.getSuccessResult();
    }

    /**
     * 안마의자 신청 취소
     * @return CommonResult - SuccessResult
     */
    @PutMapping("/cancel/massage")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자 신청 취소", notes = "안마의자 신청 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult cancelMassageMember() {
        LocalDateTime currentTime = LocalDateTime.now();
        massageService.cancelMassage(currentTime.getDayOfWeek(), currentTime.getHour(), currentTime.getMinute());
        return responseService.getSuccessResult();
    }

    /**
     * 안마의자를 신청한 학생 조회
     * @return SingleResult - (id, stuNum, memberName)
     */
    @GetMapping("/massage")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자 신청한 학생 전체 조회", notes = "안마의자 신청한 학생 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<List<MassageStudentsDto>> getMassageStudentsMember() {
        return responseService.getSingleResult(massageService.getMassageStudents());
    }

    /**
     * 안마의자를 신청한 학생수와 자신의 신청 상태 조회
     * @return Map<String count(안마의자 신청을 한 학생수), String massageStatus(자신의 안마의자 신청 상태)>
     */
    @GetMapping ("/massage/info")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자를 신청한 학생 카운트, 안마의자 신청 상태 조회", notes = "안마의자를 신청한 학생 카운트, 안마의자 신청 상태 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<Map<String, String>> getMassageStatusAndCountMember() {
        return responseService.getSingleResult(massageService.getMassageInfo());
    }

}
