package com.server.Dotori.domain.rule.service;

import com.server.Dotori.domain.rule.dto.FindStusDto;
import com.server.Dotori.domain.rule.dto.ViolationOfTheRuleResponseDto;
import com.server.Dotori.domain.rule.dto.RuleGrantDto;
import com.server.Dotori.domain.rule.dto.RulesCntAndDatesDto;
import com.server.Dotori.domain.rule.enumType.Rule;

import java.util.HashMap;
import java.util.List;

public interface RuleService {
    void grant(RuleGrantDto ruleGrantDto);
    HashMap<Rule, RulesCntAndDatesDto> findAllViolationOfTheRule(String stuNum);
    List<ViolationOfTheRuleResponseDto> findViolationOfTheRules(String stuNum);
    void deleteViolationOfTheRules(Long id);
    List<ViolationOfTheRuleResponseDto> findRuleAtMainPage();
    List<FindStusDto> findAllStudents();
    List<FindStusDto> findStusByClassId(Long id);
    List<FindStusDto> findStusByMemberName(String memberName);
}
