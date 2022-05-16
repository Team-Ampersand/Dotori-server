package com.server.Dotori.domain.rule.controller.admin;

import com.server.Dotori.domain.rule.dto.FindStusDto;
import com.server.Dotori.domain.rule.dto.ViolationOfTheRuleResponseDto;
import com.server.Dotori.domain.rule.dto.RuleGrantDto;
import com.server.Dotori.domain.rule.dto.RulesCntAndDatesDto;
import com.server.Dotori.domain.rule.enumType.Rule;
import com.server.Dotori.domain.rule.service.RuleService;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.CommonResult;
import com.server.Dotori.global.response.result.ListResult;
import com.server.Dotori.global.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/admin/rule")
public class AdminRuleController {

    private final RuleService ruleService;
    private final ResponseService responseService;

    /**
     * 규정위반을 부여하는 Controller
     * @param ruleGrantDto stuNum, rule, date
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @PostMapping("/")
    public CommonResult insertRule(@RequestBody RuleGrantDto ruleGrantDto){
        ruleService.grant(ruleGrantDto);
        return responseService.getSuccessResult();
    }

    /**
     * 단일 학생의 규정위반 정보를 전체조회하는 Controller
     * @param stuNum
     * @return CommonResult - SingleResult<HashMap<Rule, RulesCntAndDatesDto>>
     * @author 노경준
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/all/{stuNum}")
    public SingleResult<HashMap<Rule, RulesCntAndDatesDto>> findAllViolationOfTheRule(@PathVariable("stuNum") String stuNum){
        return responseService.getSingleResult(ruleService.findAllViolationOfTheRule(stuNum));
    }

    /**
     * 수정하기 페이지에서 사용될 단일 학생의 규정위반 정보를 전체조회하는 Controller
     * @param stuNum
     * @return CommonResult - SingleResult<List<FindViolationOfTheRuleResponseDto>>
     * @author 노경준
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/{stuNum}")
    public SingleResult<List<ViolationOfTheRuleResponseDto>> findViolationOfTheRules(@PathVariable("stuNum") String stuNum){
        return responseService.getSingleResult(ruleService.findViolationOfTheRules(stuNum));
    }

    /**
     * 수정하기 페이지에서 사용될 단일 학생의 규정위반 정보를 전체조회하는 Controller
     * @param id
     * @return CommonResult - SuccessResult
     * @author 노경준
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @DeleteMapping("/{id}")
    public CommonResult deleteViolationOfTheRules(@PathVariable Long id){
        ruleService.deleteViolationOfTheRules(id);
        return responseService.getSuccessResult();
    }

    /**
     * 학생들을 전체조회 하는 Controller
     * @return CommonResult - ListResult<FindStusDto>
     * @author 노경준
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/stuinfo")
    public ListResult<FindStusDto> findALLStudents() {
        return responseService.getListResult(ruleService.findAllStudents());
    }

    /**
     * 학년 반으로 학생들을 조회하는 Controller
     * @param id
     * @return CommonResult - List<FindStusDto>
     * @author 노경준
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/stuinfo/{classId}")
    public ListResult<FindStusDto> findStusByClassId(@PathVariable("classId") Long id){
        return responseService.getListResult(ruleService.findStusByClassId(id));
    }

    /**
     * 이름으로 학생을 조회하는 Controller
     * @param memberName
     * @return CommonResult - List<FindStusDto>
     * @author 노경준
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/stuinfo/members")
    public ListResult<FindStusDto> findStusByMemberName(@RequestParam(value = "membername", required = true) String memberName){
        return responseService.getListResult(ruleService.findStusByMemberName(memberName));
    }
}
