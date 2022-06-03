package com.server.Dotori.domain.massage.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.domain.massage.dto.MassageStudentsDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.server.Dotori.domain.massage.QMassage.massage;
import static com.server.Dotori.domain.member.QMember.member;

@RequiredArgsConstructor
public class MassageRepositoryImpl implements MassageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MassageStudentsDto> findByMassageList() {
        return queryFactory.from(massage)
                .select(Projections.fields(MassageStudentsDto.class,
                        massage.member.id,
                        massage.member.stuNum,
                        massage.member.memberName)
                )
                .innerJoin(massage.member, member)
                .orderBy(massage.createdDate.asc())
                .fetch();
    }
}