package com.server.Dotori.model.rule.service;

import com.server.Dotori.model.rule.dto.RuleGrantDto;
import com.server.Dotori.model.rule.dto.RulesCntAndDatesDto;
import com.server.Dotori.model.rule.enumType.Rule;

import java.util.HashMap;

public interface RuleService {
    void grant(RuleGrantDto ruleGrantDto);
    HashMap<Rule, RulesCntAndDatesDto> findViolationOfTheRules(String stuNum);
}
