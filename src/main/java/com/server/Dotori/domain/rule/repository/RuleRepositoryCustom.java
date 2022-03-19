package com.server.Dotori.domain.rule.repository;

import com.server.Dotori.domain.rule.dto.FindIdAndRuleAndDateDto;
import com.server.Dotori.domain.rule.dto.FindRuleAndDateDto;

import java.util.List;

public interface RuleRepositoryCustom {
    List<FindRuleAndDateDto> findViolationOfTheRule(String stuNum);

    List<FindIdAndRuleAndDateDto> findViolationOfTheRules(String stuNum);
}
