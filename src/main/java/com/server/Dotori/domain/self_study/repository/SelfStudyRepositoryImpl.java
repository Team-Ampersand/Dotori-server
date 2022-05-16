package com.server.Dotori.domain.self_study.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
                .where(selfStudy.member.memberName.like("%" + memberName + "%"))
                .innerJoin(selfStudy.member, member)
                .fetch();
    }

    /**
     * 학년반별로 자습신청 된 회원을 조회하는 query
     * @param id classId
     * @return List - SelfStudyStudentsDto (id, stuNum, memberName, gender)
     * @author 배태현
     */
    @Override
    public List<SelfStudyStudentsDto> findBySelfStudyCategory(Long id) {
        return queryFactory.from(selfStudy)
                .select(Projections.fields(SelfStudyStudentsDto.class,
                        member.id,
                        member.stuNum,
                        member.memberName,
                        member.gender
                        )
                )
                .where(member.stuNum.like(id+"%"))
                .innerJoin(selfStudy.member, member)
                .orderBy(member.stuNum.asc())
                .fetch();
    }
}
