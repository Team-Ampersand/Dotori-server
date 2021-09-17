package com.server.Dotori.model.member.controller.point.admin;

import com.server.Dotori.model.member.dto.PointDto;
import com.server.Dotori.model.member.service.point.PointService;
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
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminPointController {

    private final PointService pointService;
    private final ResponseService responseService;

    /**
     * 반별 상벌점 조회 컨트롤러
     * @param id classId
     * @return SingleResult - 반별학생, 상벌점
     * @author 배태현
     */
    @GetMapping("/point/{classId}")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "반별 상벌점 조회", notes = "반별 상벌점 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getAllStudentsPointAdmin(@PathVariable("classId") Long id) {
        return responseService.getSingleResult(pointService.getAllStudentPoint(id));
    }

    /**
     * 상벌점 부여 컨트롤러
     * @param pointDto (receiverId, point)
     * @return CommonResult - SuccessResult
     * @author 배태현
     */
    @PostMapping("/point")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "상벌점 부여", notes = "상벌점 부여")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult givePointAdmin(@RequestBody PointDto pointDto) {
        pointService.point(pointDto);
        return responseService.getSuccessResult();
    }
}
