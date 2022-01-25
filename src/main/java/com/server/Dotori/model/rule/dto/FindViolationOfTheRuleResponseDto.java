package com.server.Dotori.model.rule.dto;

import com.server.Dotori.model.rule.enumType.Rule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FindViolationOfTheRuleResponseDto {
    private Long id;
    private Rule rule;
    private String createdDate;
}