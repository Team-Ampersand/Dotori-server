package com.server.Dotori.domain.rule.dto;

import com.server.Dotori.domain.rule.enumType.Rule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ViolationOfTheRuleResponseDto {
    private Long id;
    private Rule rule;
    private LocalDate date;
}
