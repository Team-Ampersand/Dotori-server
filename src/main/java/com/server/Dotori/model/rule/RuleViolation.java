package com.server.Dotori.model.rule;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.rule.enumType.Rule;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
@Entity @Table(name = "RuleViolation")
public class RuleViolation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_violation_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_violation_rule", nullable = false)
    private Rule rule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
