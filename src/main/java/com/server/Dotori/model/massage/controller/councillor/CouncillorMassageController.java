package com.server.Dotori.model.massage.controller.councillor;

import com.server.Dotori.model.massage.dto.MassageStudentsDto;
import com.server.Dotori.model.massage.service.MassageService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
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
@RequestMapping("/v1/councillor")
@RequiredArgsConstructor
public class CouncillorMassageController {

    private final MassageService massageService;
    private final ResponseService responseService;

    @PutMapping("/massage")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자 신청", notes = "안마의자 신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult requestMassageCouncillor() {
        LocalDateTime currentTime = LocalDateTime.now();
        massageService.requestMassage(currentTime.getDayOfWeek(), currentTime.getHour(), currentTime.getMinute());
        return responseService.getSuccessResult();
    }

    @PutMapping("/cancel/massage")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자 신청 취소", notes = "안마의자 신청 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult cancelMassageCouncillor() {
        LocalDateTime currentTime = LocalDateTime.now();
        massageService.cancelMassage(currentTime.getDayOfWeek(), currentTime.getHour(), currentTime.getMinute());
        return responseService.getSuccessResult();
    }

    @GetMapping("/massage")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자 신청한 학생 전체 조회", notes = "안마의자 신청한 학생 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<List<MassageStudentsDto>> getMassageStudentsCouncillor() {
        return responseService.getSingleResult(massageService.getMassageStudents());
    }

    @GetMapping ("/massage/info")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자를 신청한 학생 카운트, 안마의자 신청 상태 조회", notes = "안마의자를 신청한 학생 카운트, 안마의자 신청 상태 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<Map<String, String>> getMassageStatusAndCountCouncillor() {
        return responseService.getSingleResult(massageService.getMassageStatusAndCount());
    }

}
