package com.server.Dotori.domain.rule.dto;

import com.server.Dotori.domain.rule.enumType.Rule;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class RuleGrantDto {

    private List<String> stuNum;
    private List<Rule> rule;
    private LocalDate date;

}
