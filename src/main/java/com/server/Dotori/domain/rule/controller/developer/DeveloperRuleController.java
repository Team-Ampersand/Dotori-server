package com.server.Dotori.domain.rule.controller.developer;

import com.server.Dotori.domain.rule.dto.ViolationOfTheRuleResponseDto;
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
@RequestMapping("/v1/developer/rule")
public class DeveloperRuleController {

    private final RuleService ruleService;
    private final ResponseService responseService;

    /**
     * 메인 페이지에서 본인의 규정위반 내역을 확인하는 Controller
     * @return SingleResult - List<FindViolationOfTheRuleResponseDto>
     * @author 노경준
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    })
    @GetMapping("/main")
    public SingleResult<List<ViolationOfTheRuleResponseDto>> findRuleAtMainPageDeveloper(){
        return responseService.getSingleResult(ruleService.findRuleAtMainPage());
    }
}
