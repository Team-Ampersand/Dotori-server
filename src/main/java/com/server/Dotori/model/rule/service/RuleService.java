package com.server.Dotori.model.rule.service;

import com.server.Dotori.model.rule.dto.RuleGrantDto;

import java.util.List;

public interface RuleService {
    void grant(RuleGrantDto ruleGrantDto);
}
