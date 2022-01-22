package com.server.Dotori.model.massage.controller.develop;

import com.server.Dotori.model.massage.service.MassageService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/develop")
@RequiredArgsConstructor
public class DevelopMassageController {

    private final MassageService massageService;
    private final ResponseService responseService;

    @PutMapping("/massage")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자 신청", notes = "안마의자 신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult requestMassage() {
        LocalDateTime currentTime = LocalDateTime.now();
        massageService.requestMassage(currentTime.getDayOfWeek(), currentTime.getHour(), currentTime.getMinute());
        return responseService.getSuccessResult();
    }
}
