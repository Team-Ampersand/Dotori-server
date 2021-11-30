package com.server.Dotori.model.member.controller.selfstudy.member;

import com.server.Dotori.model.member.service.selfstudy.SelfStudyService;
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

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberSelfStudyController {

    private final ResponseService responseService;
    private final SelfStudyService selfStudyService;

    /**
     * 자습신청 컨트롤러
     * @return CommonResult - SuccessResult
     * @author 배태현
     */
    @PutMapping("/selfstudy")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "자습신청", notes = "자습신청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult requestSelfStudy() {
        LocalDateTime currentTime = LocalDateTime.now();
        selfStudyService.requestSelfStudy(currentTime.getDayOfWeek(), currentTime.getHour());
        return responseService.getSuccessResult();
    }

    /**
     * 자습신청 취소 컨트롤러
     * @return CommonResult SuccessResult
     * @author 배태현
     */
    @PutMapping("/cancel/selfstudy")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "자습신청 취소", notes = "자습신청 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult cancelSelfStudyMember() {
        LocalDateTime currentTime = LocalDateTime.now();
        selfStudyService.cancelSelfStudy(currentTime.getDayOfWeek(), currentTime.getHour());
        return responseService.getSuccessResult();
    }

    /**
     * 자습신청한 학생 전체 조회 컨트롤러
     * @return SingleResult (id, stuNum, username)
     * @author 배태현
     */
    @GetMapping("/selfstudy")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "자습신청한 학생 전체 조회", notes = "자습신청한 학생 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getSelfStudyStudentsMember() {
        return responseService.getSingleResult(selfStudyService.getSelfStudyStudents());
    }

    /**
     * 자습신청한 학생 반별 조회 컨트롤러
     * @param id classId
     * @return SingleResult (id, stuNum, username)
     * @author 배태현
     */
    @GetMapping("/selfstudy/{classId}")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "자습신청한 학생 반별 조회", notes = "자습신청한 학생 반별 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getSelfStudyStudentsByCategoryMember(@PathVariable("classId") Long id) {
        return responseService.getSingleResult(selfStudyService.getSelfStudyStudentsByCategory(id));
    }

    /**
     * 자습신청한 학생 카운트 수 조회 컨트롤러
     * @return SingleResult - count
     * @author 배태현
     */
    @GetMapping ("/selfstudy/count")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "자습신청한 학생 카운트 조회", notes = "자습신청한 학생 카운트 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult selfStudyTotalCountMember() {
        return responseService.getSingleResult(selfStudyService.selfStudyCount());
    }

    /**
     * 자습신청 상태 조회 컨트롤러
     * @return SingleResult - SelfStudy
     * @author 배태현
     */
    @GetMapping("/selfstudy/status")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "현재 자습신청 상태 조회", notes = "현재 자습신청 상태 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult selfStudyStatusMember() {
        return responseService.getSingleResult(selfStudyService.getCurrentSelfStudyStatus());
    }
}
