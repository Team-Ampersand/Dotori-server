package com.server.Dotori.domain.self_study.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.domain.member.enumType.SelfStudyStatus;
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
                        selfStudy.member.selfStudy.eq(SelfStudyStatus.APPLIED)
                )
                .innerJoin(selfStudy.member, member)
                .orderBy(selfStudy.createdDate.asc())
                .fetch();
    }

    @Override
    public List<SelfStudyStudentsDto> findByMemberName(String memberName) {
        return queryFactory.from(selfStudy)
                .select(Projections.fields(SelfStudyStudentsDto.class,
                        selfStudy.member.id,
                        selfStudy.member.stuNum,
                        selfStudy.member.memberName,
                        selfStudy.member.gender
                        )
                )
                .where(
                        selfStudy.member.selfStudy.eq(SelfStudyStatus.APPLIED)
                        .and(selfStudy.member.memberName.like("%" + memberName + "%"))
                )
                .innerJoin(selfStudy.member, member)
                .fetch();
    }
}
