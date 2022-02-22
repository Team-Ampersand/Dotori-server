package com.server.Dotori.exception.rule.controller;

import com.server.Dotori.exception.rule.exception.RuleNoHistoryException;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class RuleExceptionController {

    @GetMapping("/rule-no-history")
    public CommonResult ruleNoHistoryException() {
        throw new RuleNoHistoryException();
    }

}
