package com.server.Dotori.model.member.controller.studentinfo.developer;

import com.server.Dotori.model.member.dto.RoleUpdateDto;
import com.server.Dotori.model.member.dto.StuNumUpdateDto;
import com.server.Dotori.model.member.dto.UsernameUpdateDto;
import com.server.Dotori.model.member.service.studentinfo.StuInfoService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/developer")
@RequiredArgsConstructor
public class DeveloperStuInfoController {

    private final ResponseService responseService;
    private final StuInfoService stuInfoService;

    /**
     * 학생정보 변경을 위해 반별로 학생을 조회하는 컨트롤러
     * @param id classId
     * @return SingleResult - List (id, stuNum, username, role)
     */
    @GetMapping("/info/{classId}")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "학생정보 변경 반별 학생 조회", notes = "학생정보 변경 반별 학생 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getStudentInfoDeveloper(@PathVariable("classId") Long id) {
        return responseService.getSingleResult(stuInfoService.getStudentInfo(id));
    }

    /**
     * 학생 정보 변경 - 권한 변경
     * @param roleUpdateDto (receiverId, role)
     * @return CommonResult - SuccessResult
     */
    @PutMapping("/info/role")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "권한 변경", notes = "권한 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult updateRoleDeveloper(@RequestBody RoleUpdateDto roleUpdateDto) {
        stuInfoService.updateRole(roleUpdateDto);
        return responseService.getSuccessResult();
    }

    /**
     * 학생 정보 변경 - 학번 변경
     * @param stuNumUpdateDto (receiverId, stuNum)
     * @return CommonResult - SuccessResult
     */
    @PutMapping("/info/stunum")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "학번 변경", notes = "학번 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult updateStuNumDeveloper(@RequestBody StuNumUpdateDto stuNumUpdateDto) {
        stuInfoService.updateStuNum(stuNumUpdateDto);
        return responseService.getSuccessResult();
    }

    /**
     * 학생 정보 변경 - 이름 변경
     * @param usernameUpdateDto (receiverId, username)
     * @return CommonResult - SuccessResult
     */
    @PutMapping("/info/username")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "이름 변경", notes = "이름 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult updateUsernameDeveloper(@RequestBody UsernameUpdateDto usernameUpdateDto) {
        stuInfoService.updateUsername(usernameUpdateDto);
        return responseService.getSuccessResult();
    }
}