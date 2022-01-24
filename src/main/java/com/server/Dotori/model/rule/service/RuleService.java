package com.server.Dotori.model.rule.service;

import com.server.Dotori.model.rule.dto.FindViolationOfTheRuleResponseDto;
import com.server.Dotori.model.rule.dto.RuleGrantDto;
import com.server.Dotori.model.rule.dto.RulesCntAndDatesDto;
import com.server.Dotori.model.rule.enumType.Rule;

import java.util.HashMap;
import java.util.List;

public interface RuleService {
    void grant(RuleGrantDto ruleGrantDto);
    HashMap<Rule, RulesCntAndDatesDto> findAllViolationOfTheRule(String stuNum);
    List<FindViolationOfTheRuleResponseDto> findViolationOfTheRules(String stuNum);
}
