package com.server.Dotori.model.massage.controller.admin;

import com.server.Dotori.model.massage.service.MassageService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminMassageController {

    private final MassageService massageService;
    private final ResponseService responseService;

    @GetMapping("/massage")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "안마의자 신청한 학생 전체 조회", notes = "안마의자 신청한 학생 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getMassageStudentsAdmin() {
        return responseService.getSingleResult(massageService.getMassageStudents());
    }

}
