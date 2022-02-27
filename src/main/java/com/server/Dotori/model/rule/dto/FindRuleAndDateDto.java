package com.server.Dotori.model.rule.dto;

import com.server.Dotori.model.rule.enumType.Rule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class FindRuleAndDateDto {

    private Rule rules;
    private LocalDate dates;

}
