package com.server.Dotori.model.rule.dto;

import com.server.Dotori.model.rule.enumType.Rule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FindRulesAndDatesDto {

    private Rule rules;
    private LocalDateTime dates;

    public FindRulesAndDatesDto() {
    }

    public FindRulesAndDatesDto(Rule rule, LocalDateTime dates) {
        this.rules = rule;
        this.dates = dates;
    }
}
