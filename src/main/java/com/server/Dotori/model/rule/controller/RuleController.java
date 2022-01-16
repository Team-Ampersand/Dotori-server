package com.server.Dotori.model.rule.controller;

import com.server.Dotori.model.rule.dto.RuleGrantDto;
import com.server.Dotori.model.rule.service.RuleService;
import com.server.Dotori.response.ResponseService;
import com.server.Dotori.response.result.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/rule")
public class RuleController {

    private final RuleService ruleService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header"),
    })
    @PostMapping("/grant")
    public CommonResult grant(@RequestBody RuleGrantDto ruleGrantDto){
        ruleService.grant(ruleGrantDto);
        return responseService.getSuccessResult();
    }

}
