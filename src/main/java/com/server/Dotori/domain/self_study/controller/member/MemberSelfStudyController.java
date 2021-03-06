package com.server.Dotori.domain.self_study.controller.member;

import com.server.Dotori.domain.self_study.service.SelfStudyService;
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
import java.util.Map;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberSelfStudyController {

    private final ResponseService responseService;
    private final SelfStudyService selfStudyService;

    /**
     * 자습신청 컨트롤러
     *
     * @return CommonResult - SuccessResult
     * @author 배태현
     */
    @PutMapping("/selfstudy")
    @ResponseStatus(HttpStatus.OK)
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
     *
     * @return CommonResult SuccessResult
     * @author 배태현
     */
    @PutMapping("/cancel/selfstudy")
    @ResponseStatus(HttpStatus.OK)
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
     * 자습신청한 학생 이름 검색 컨트롤러
     *
     * @return SingleResult (id, stuNum, username, gender)
     * @author 배태현
     */
    @GetMapping("/selfstudy")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "자습신청한 학생 이름 검색", notes = "자습신청한 학생 이름 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getSelfStudyStudentByMemberNameMember(@RequestParam(value = "memberName") String memberName) {
        return responseService.getSingleResult(selfStudyService.getSelfStudyStudentByMemberName(memberName));
    }

    /**
     * 자습신청 한 학생 자습신청 한 순서대로 전체조회
     *
     * @return SingleResult (id, stuNum, username)
     * @author 배태현
     */
    @GetMapping("/selfstudy/rank")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "자습신청한 학생 자습 신청한 순서대로 전체 조회", notes = "자습신청한 학생 자습 신청한 순서대로 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getSelfStudyStudentsByCreateDateMember() {
        return responseService.getSingleResult(selfStudyService.getSelfStudyStudentsByCreateDate());
    }

    /**
     * 자습신청한 학생 반별 조회 컨트롤러
     *
     * @param id classId
     * @return SingleResult (id, stuNum, username)
     * @author 배태현
     */
    @GetMapping("/selfstudy/{classId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "자습신청한 학생 반별 조회", notes = "자습신청한 학생 반별 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult getSelfStudyStudentsByCategoryMember(@PathVariable("classId") Long id) {
        return responseService.getSingleResult(selfStudyService.getSelfStudyStudentsByCategory(id));
    }

    /**
     * 자습신청한 학생 카운트 수, 자습신청 상태 조회 컨트롤러
     *
     * @return SingleResult - count, selfStudy_status
     * @author 배태현
     */
    @GetMapping("/selfstudy/info")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "자습신청한 학생 카운트, 자습신청 상태 조회", notes = "자습신청한 학생 카운트, 자습신청 상태 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<Map<String, String>> selfStudyTotalCountMember() {
        return responseService.getSingleResult(selfStudyService.selfStudyInfo());
    }
}
