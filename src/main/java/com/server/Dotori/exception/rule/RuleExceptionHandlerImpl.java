package com.server.Dotori.exception.rule;

import com.server.Dotori.exception.rule.exception.RuleNoHistoryException;
import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.util.ExceptionResponseObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RuleExceptionHandlerImpl implements RuleExceptionHandler{

    private final ExceptionResponseObjectUtil exceptionResponseObjectUtil;

    @Override
    public CommonResult ruleNoHistoryException(RuleNoHistoryException ex) {
        log.debug("=== Rule No History Exception 발생 ===");
        return exceptionResponseObjectUtil.getExceptionResponseObject(RULE_NO_HISTORY);
    }
}
