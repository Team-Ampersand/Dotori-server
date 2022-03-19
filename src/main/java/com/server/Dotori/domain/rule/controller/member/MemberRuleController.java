package com.server.Dotori.domain.rule.controller.member;

import com.server.Dotori.domain.rule.dto.FindViolationOfTheRuleResponseDto;
import com.server.Dotori.domain.rule.service.RuleService;
import com.server.Dotori.global.response.ResponseService;
import com.server.Dotori.global.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/member/rule")
public class MemberRuleController {

    private final RuleService ruleService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/main")
    public SingleResult<List<FindViolationOfTheRuleResponseDto>> findRuleAtMainPageMember(){
        return responseService.getSingleResult(ruleService.findRuleAtMainPage());
    }
}