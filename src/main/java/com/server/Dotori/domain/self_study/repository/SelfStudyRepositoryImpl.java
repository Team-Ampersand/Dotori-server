package com.server.Dotori.domain.self_study.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.domain.member.enumType.SelfStudy;
import com.server.Dotori.domain.self_study.dto.SelfStudyStudentsDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.server.Dotori.domain.member.QMember.member;
import static com.server.Dotori.domain.self_study.QSelfStudy.selfStudy;

@RequiredArgsConstructor
public class SelfStudyRepositoryImpl implements SelfStudyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SelfStudyStudentsDto> findByCreateDate() {
        return queryFactory.from(selfStudy)
                .select(Projections.fields(SelfStudyStudentsDto.class,
                        selfStudy.member.id,
                        selfStudy.member.stuNum,
                        selfStudy.member.memberName,
                        selfStudy.member.gender
                        )
                )
                .where(
                        selfStudy.member.selfStudy.eq(SelfStudy.APPLIED)
                )
                .innerJoin(selfStudy.member, member)
                .orderBy(selfStudy.createdDate.asc())
                .fetch();
    }
}
