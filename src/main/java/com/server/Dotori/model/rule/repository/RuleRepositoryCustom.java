package com.server.Dotori.model.rule.repository;

import com.server.Dotori.model.rule.dto.FindRulesAndDatesDto;

import java.util.List;

public interface RuleRepositoryCustom {
    List<FindRulesAndDatesDto> findViolationOfTheRules(String stuNum);
}
