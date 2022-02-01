package com.server.Dotori.model.rule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.model.rule.dto.FindIdAndRuleAndDateDto;
import com.server.Dotori.model.rule.dto.FindRuleAndDateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.server.Dotori.model.member.QMember.member;
import static com.server.Dotori.model.rule.QRuleViolation.ruleViolation;

@RequiredArgsConstructor
@Repository
public class RuleRepositoryCustomImpl implements RuleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    /**
     * 기숙사 규정을 위반한 학생의 이력을 모두 조회하는 쿼리
     * @param stuNum
     * @return result - List<FindRulesAndDatesDto> - rules, date
     */
    @Override
    public List<FindRuleAndDateDto> findViolationOfTheRule(String stuNum) {
        List<FindRuleAndDateDto> result = queryFactory
                .select(Projections.constructor(FindRuleAndDateDto.class,
                        ruleViolation.rule,
                        ruleViolation.date))
                .from(ruleViolation)
                .innerJoin(ruleViolation.member, member)
                .where(ruleViolation.member.stuNum.eq(stuNum))
                .fetch();

        return result;
    }

    @Override
    public List<FindIdAndRuleAndDateDto> findViolationOfTheRules(String stuNum) {
        List<FindIdAndRuleAndDateDto> result = queryFactory
                .select(Projections.constructor(FindIdAndRuleAndDateDto.class,
                        ruleViolation.id,
                        ruleViolation.rule,
                        ruleViolation.date))
                .from(ruleViolation)
                .innerJoin(ruleViolation.member, member)
                .where(ruleViolation.member.stuNum.eq(stuNum))
                .orderBy(ruleViolation.date.desc())
                .fetch();

        return result;
    }
}
