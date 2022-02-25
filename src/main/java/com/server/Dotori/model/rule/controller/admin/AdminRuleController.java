package com.server.Dotori.model.rule.controller.admin;

import com.server.Dotori.model.rule.dto.*;
import com.server.Dotori.model.rule.enumType.Rule;
import com.server.Dotori.model.rule.service.RuleService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.ListResult;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/admin/rule")
public class AdminRuleController {

    private final RuleService ruleService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @PostMapping("/")
    public CommonResult insertRule(@RequestBody RuleGrantDto ruleGrantDto){
        ruleService.grant(ruleGrantDto);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/all/{stuNum}")
    public SingleResult<HashMap<Rule, RulesCntAndDatesDto>> findAllViolationOfTheRule(@PathVariable("stuNum") String stuNum){
        return responseService.getSingleResult(ruleService.findAllViolationOfTheRule(stuNum));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/{stuNum}")
    public CommonResult findViolationOfTheRules(@PathVariable("stuNum") String stuNum){
        return responseService.getSingleResult(ruleService.findViolationOfTheRules(stuNum));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @DeleteMapping("/{id}")
    public CommonResult deleteViolationOfTheRules(@PathVariable Long id){
        ruleService.deleteViolationOfTheRules(id);
        return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/stuinfo")
    public ListResult<FindStusDto> findALLStudents() {
        return responseService.getListResult(ruleService.findAllStudents());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/stuinfo/{classId}")
    public ListResult<FindStusDto> findStusByClassId(@PathVariable("classId") Long id){
        return responseService.getListResult(ruleService.findStusByClassId(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/stuinfo/members")
    public ListResult<FindStusDto> findStusByMemberName(@RequestParam(value = "membername", required = true) String memberName){
        return responseService.getListResult(ruleService.findStusByMemberName(memberName));
    }
}
