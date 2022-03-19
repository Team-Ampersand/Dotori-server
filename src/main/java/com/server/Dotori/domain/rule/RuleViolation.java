package com.server.Dotori.domain.rule;

import com.server.Dotori.global.entity.BaseTimeEntity;
import com.server.Dotori.domain.rule.enumType.Rule;
import com.server.Dotori.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @Getter
@Entity @Table(name = "RuleViolation")
public class RuleViolation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_violation_id")
    private Long id;

    @Column(name = "rule_violation_date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_violation_rule", nullable = false)
    private Rule rule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
