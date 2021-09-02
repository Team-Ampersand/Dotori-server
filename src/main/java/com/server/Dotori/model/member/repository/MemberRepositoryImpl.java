package com.server.Dotori.model.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.dto.SelfStudyStudentsDto;
import com.server.Dotori.model.member.enumType.SelfStudy;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.server.Dotori.model.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SelfStudyStudentsDto> findBySelfStudyAPLLIED() {

        return queryFactory.from(member)
                .select(Projections.constructor(SelfStudyStudentsDto.class,
                        member.stdNum,
                        member.username)
                ).where(
                        member.selfStudy.eq(SelfStudy.APPLIED)
                )
                .orderBy(member.stdNum.asc())
                .fetch();
    }

    @Override
    public void updateSelfStudyStatus() {

        queryFactory
                .update(member)
                .where(
                        member.selfStudy.eq(SelfStudy.APPLIED)
                                .or(member.selfStudy.eq(SelfStudy.CANT))
                )
                .set(member.selfStudy, SelfStudy.CAN)
                .execute();
    }

    @Override
    public List<GetAboutPointDto> findStudentPoint(Long id) {
        return queryFactory.from(member)
                .select(Projections.constructor(GetAboutPointDto.class,
                        member.id,
                        member.stdNum,
                        member.username,
                        member.point
                        ))
                .where(member.stdNum.like(id+"%"))
                .orderBy(member.stdNum.asc())
                .fetch();
    }

    @Override
    public GetAboutPointDto findProfileByMember(Member memberEntity) {
        return queryFactory.from(member)
                .select(Projections.constructor(GetAboutPointDto.class,
                        member.id,
                        member.stdNum,
                        member.username,
                        member.point
                ))
                .where(member.eq(memberEntity))
                .fetchOne();
    }
}
