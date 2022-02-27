package com.server.Dotori.model.member.controller.selfstudy.admin;

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

import java.util.Map;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminSelfStudyController {

    private final ResponseService responseService;
    private final SelfStudyService selfStudyService;

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
    public SingleResult getSelfStudyStudentsAdmin() {
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
    public SingleResult getSelfStudyStudentsByCategoryAdmin(@PathVariable("classId") Long id) {
        return responseService.getSingleResult(selfStudyService.getSelfStudyStudentsByCategory(id));
    }

    /**
     * 자습신청한 학생 카운트 수, 자습신청 상태 조회 컨트롤러
     * @return SingleResult - count, selfStudy_status
     * @author 배태현
     */
    @GetMapping ("/selfstudy/info")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "자습신청한 학생 카운트, 자습신청 상태 조회", notes = "자습신청한 학생 카운트, 자습신청 상태 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public SingleResult<Map<String, String>> selfStudyTotalCountAdmin() {
        return responseService.getSingleResult(selfStudyService.selfStudyInfo());
    }

    /**
     * 자습신청을 금지시키는 컨트롤러
     * @param id memberId
     * @return SuccessResult
     * @author 배태현
     */
    @PutMapping("/selfstudy/ban/{id}")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "자습신청 금지", notes = "자습신청 금지")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult banSelfStudyAdmin(@PathVariable("id") Long id) {
        selfStudyService.banSelfStudy(id);
        return responseService.getSuccessResult();
    }

    /**
     * 자습신청을 금지를 취소하는 컨트롤러
     * @param id memberId
     * @return SuccessResult
     * @author 배태현
     */
    @PutMapping("/selfstudy/ban/cancel/{id}")
    @ResponseStatus( HttpStatus.OK )
    @ApiOperation(value = "자습신청 금지 취소", notes = "자습신청 금지 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult banCancelSelfStudyAdmin(@PathVariable("id") Long id) {
        selfStudyService.cancelBanSelfStudy(id);
        return responseService.getSuccessResult();
    }
}
