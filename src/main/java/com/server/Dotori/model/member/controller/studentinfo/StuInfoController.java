package com.server.Dotori.model.member.controller.studentinfo;

import com.server.Dotori.model.member.dto.RoleUpdateDto;
import com.server.Dotori.model.member.dto.StudentInfoDto;
import com.server.Dotori.model.member.service.studentinfo.StuInfoService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class StuInfoController {

    private final ResponseService responseService;
    private final StuInfoService stuInfoService;

    @GetMapping ("/list/{classId}")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getStudentInfo(@PathVariable("classId") Long id) {
        return responseService.getSingleResult(stuInfoService.getStudentInfo(id));
    }

    @PutMapping("/list/role")
    @ResponseStatus( HttpStatus.OK )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult updateRole(@RequestBody RoleUpdateDto roleUpdateDto) {
        stuInfoService.updateRole(roleUpdateDto);
        return responseService.getSuccessResult();
    }
}
