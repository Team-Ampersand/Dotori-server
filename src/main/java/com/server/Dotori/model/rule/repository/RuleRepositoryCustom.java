package com.server.Dotori.model.rule.repository;

import com.server.Dotori.model.rule.dto.FindIdAndRuleAndDateDto;
import com.server.Dotori.model.rule.dto.FindRuleAndDateDto;

import java.util.List;

public interface RuleRepositoryCustom {
    List<FindRuleAndDateDto> findViolationOfTheRule(String stuNum);

    List<FindIdAndRuleAndDateDto> findViolationOfTheRules(String stuNum);
}
