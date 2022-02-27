package com.server.Dotori.exception.rule;

import com.server.Dotori.exception.rule.exception.RuleNoHistoryException;
import com.server.Dotori.response.result.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface RuleExceptionHandler {

    String RULE_NO_HISTORY = "rule-no-history";

    @ExceptionHandler(RuleNoHistoryException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CommonResult ruleNoHistoryException(RuleNoHistoryException ex);
}
