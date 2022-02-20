package com.server.Dotori.model.rule.dto;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.rule.RuleViolation;
import com.server.Dotori.model.rule.enumType.Rule;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter
@Getter
public class RuleGrantDto {

    private List<String> stuNum;
    private Rule rule;
    private LocalDate date;

    public RuleViolation toEntity(Member member){
        return RuleViolation.builder()
                .member(member)
                .rule(rule)
                .date(date)
                .build();
    }
}
