package com.server.Dotori.model.rule.controller.councillor;

import com.server.Dotori.model.rule.dto.FindViolationOfTheRuleResponseDto;
import com.server.Dotori.model.rule.service.RuleService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/councillor/rule")
public class CouncillorRuleController {

    private final RuleService ruleService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/main")
    public SingleResult<List<FindViolationOfTheRuleResponseDto>> findRuleAtMainPageCouncillor(){
        return responseService.getSingleResult(ruleService.findRuleAtMainPage());
    }
}
