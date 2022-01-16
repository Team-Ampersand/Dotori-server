package com.server.Dotori.model.rule.dto;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.rule.RuleViolation;
import com.server.Dotori.model.rule.enumType.Rule;
import lombok.*;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class RuleGrantDto {

    private List<Long> memberId;
    private Rule rule;

    public RuleViolation toEntity(Member member, Rule rule){
        return RuleViolation.builder()
                .member(member)
                .rule(rule)
                .build();
    }
}
