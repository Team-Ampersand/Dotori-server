package com.server.Dotori.model.rule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.model.rule.dto.FindRulesAndDatesDto;
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
     * 기숙사 규정을 어긴 학생의 이력을 모두 조회하는 쿼리
     * @param stuNum
     * @return result - List<FindRulesAndDatesDto> - rules, date
     */
    @Override
    public List<FindRulesAndDatesDto> findViolationOfTheRules(String stuNum) {
        List<FindRulesAndDatesDto> result = queryFactory
                .select(Projections.constructor(FindRulesAndDatesDto.class,
                        ruleViolation.rule,
                        ruleViolation.createdDate))
                .from(ruleViolation)
                .innerJoin(ruleViolation.member, member)
                .where(ruleViolation.member.stuNum.eq(stuNum))
                .fetch();

        return result;
    }
}
