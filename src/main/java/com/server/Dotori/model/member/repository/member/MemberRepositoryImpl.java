package com.server.Dotori.model.member.repository.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.dto.SelfStudyStudentsDto;
import com.server.Dotori.model.member.enumType.Massage;
import com.server.Dotori.model.member.enumType.SelfStudy;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.server.Dotori.model.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 자습신청 상태가 '신청됨' 상태인 회원들을 학번별로 오름순으로 조회하는 query
     * @return List - SelfStudyStudentsDto (id, stuNum, username)
     * @author 배태현
     */
    @Override
    public List<SelfStudyStudentsDto> findBySelfStudyAPLLIED() {

        return queryFactory.from(member)
                .select(Projections.fields(SelfStudyStudentsDto.class,
                        member.id,
                        member.stuNum,
                        member.memberName)
                ).where(
                        member.selfStudy.eq(SelfStudy.APPLIED)
                )
                .orderBy(member.stuNum.asc())
                .fetch();
    }

    /**
     * 학년반별로 자습신청 상태가 '신청됨' 인 회원을 조회하는 query
     * @param id classId
     * @return List - SelfStudyStudentsDto (id, stuNum, username)
     * @author 배태현
     */
    @Override
    public List<SelfStudyStudentsDto> findBySelfStudyCategory(Long id) {
        return queryFactory.from(member)
                .select(Projections.fields(SelfStudyStudentsDto.class,
                        member.id,
                        member.stuNum,
                        member.memberName))
                .where(
                        member.selfStudy.eq(SelfStudy.APPLIED)
                        .and(member.stuNum.like(id+"%"))
                )
                .orderBy(member.stuNum.asc())
                .fetch();
    }

    /**
     * 자습신청 상태가 '신청됨', '취소' 상태인 회원의 자습신청 상태를 '가능'으로 update하는 쿼리
     * @author 배태현
     */
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

    /**
     * 학년반별로 상벌점 목록을 조회하는 query (학번 오름차순)
     * @param id classId
     * @return List - GetAboutPointDto (id, stuNum, username, point)
     * @author 배태현
     */
    @Override
    public List<GetAboutPointDto> findStudentPoint(Long id) {
        return queryFactory.from(member)
                .select(Projections.constructor(GetAboutPointDto.class,
                        member.id,
                        member.stuNum,
                        member.memberName,
                        member.point
                        ))
                .where(member.stuNum.like(id+"%"))
                .orderBy(member.stuNum.asc())
                .fetch();
    }

    /**
     * 현재 로그인된 유저의 프로필 정보(메인페이지)를 조회하는 query
     * @param memberEntity currentUser
     * @return GetAboutPointDto (id, username, stNum, point)
     * @author 배태현
     */
    @Override
    public GetAboutPointDto findProfileByMember(Member memberEntity) {
        return queryFactory.from(member)
                .select(Projections.constructor(GetAboutPointDto.class,
                        member.id,
                        member.stuNum,
                        member.memberName,
                        member.point
                ))
                .where(member.eq(memberEntity))
                .fetchOne();
    }

    /**
     * 반별로 학생정보를 조회하는 query
     * @param id classId
     * @return List - Member
     * @author 배태현
     */
    @Override
    public List<Member> findStudentInfo(Long id) {
        return queryFactory.from(member)
                .select(member)
                .where(member.stuNum.like(id+"%"))
                .orderBy(member.stuNum.asc())
                .fetch();
    }

    /**
     * 학생정보 전체조회 query
     * @return List - Member
     * @author 배태현
     */
    @Override
    public List<Member> findAllStudentInfo() {
        return queryFactory.from(member)
                .select(member)
                .orderBy(member.stuNum.asc())
                .fetch();
    }

    /**
     * 자습신청 금지 상태이며 자습신청 만료기간이 다 된 학생 <br>
     * 자습신청 금지를 해제하고 자습신청 만료기간을 초기화하는 쿼리
     * @author 배태현
     */
    @Override
    public void updateUnBanSelfStudy() {
        queryFactory
                .update(member)
                .where(
                        member.selfStudy.eq(SelfStudy.IMPOSSIBLE)
                        .and(member.selfStudyExpiredDate.stringValue().substring(0,10).eq(String.valueOf(LocalDate.now())))
                )
                .set(member.selfStudy, SelfStudy.CAN)
                .set(member.selfStudyExpiredDate, (LocalDateTime) null)
                .execute();
    }

    @Override
    public void updateUnBanMassage() {
        queryFactory
                .update(member)
                .where(
                        member.massage.eq(Massage.APPLIED)
                                .and(member.massageExpiredDate.stringValue().substring(0,10).eq(String.valueOf(LocalDate.now())))
                )
                .set(member.massage, Massage.CAN)
                .set(member.massageExpiredDate, (LocalDateTime) null)
                .execute();
    }

}
