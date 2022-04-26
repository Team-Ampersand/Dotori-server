package com.server.Dotori.domain.member.repository.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.server.Dotori.domain.main_page.dto.GetProfileDto;
import com.server.Dotori.domain.massage.dto.MassageStudentsDto;
import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.enumType.MassageStatus;
import com.server.Dotori.domain.member.enumType.SelfStudyStatus;
import com.server.Dotori.domain.point.dto.GetAboutPointDto;
import com.server.Dotori.domain.rule.dto.FindStusDto;
import com.server.Dotori.domain.self_study.dto.SelfStudyStudentsDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.server.Dotori.domain.member.QMember.member;


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
                        member.memberName,
                        member.gender
                        )
                ).where(
                        member.selfStudy.eq(SelfStudyStatus.APPLIED)
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
                        member.memberName,
                        member.gender
                        )
                )
                .where(
                        member.selfStudy.eq(SelfStudyStatus.APPLIED)
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
                        member.selfStudy.eq(SelfStudyStatus.APPLIED)
                                .or(member.selfStudy.eq(SelfStudyStatus.CANT))
                )
                .set(member.selfStudy, SelfStudyStatus.CAN)
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
     * @return GetAboutPointDto (id, username, stNum, gender)
     * @author 배태현, 노경준
     */
    @Override
    public GetProfileDto findProfileByMember(Member memberEntity) {
        return queryFactory.from(member)
                .select(Projections.constructor(GetProfileDto.class,
                        member.id,
                        member.stuNum,
                        member.memberName,
                        member.gender
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
                        member.selfStudy.eq(SelfStudyStatus.IMPOSSIBLE)
                        .and(member.selfStudyExpiredDate.stringValue().substring(0,10).eq(String.valueOf(LocalDate.now())))
                )
                .set(member.selfStudy, SelfStudyStatus.CAN)
                .set(member.selfStudyExpiredDate, (LocalDateTime) null)
                .execute();
    }

    /**
     * 안마의자 신청을 한 학생들의 상태를 CAN으로 변경해주는 쿼리
     * @author 김태민
     */
    @Override
    public void updateMassageStatus() {
        queryFactory
                .update(member)
                .where(
                        member.massage.eq(MassageStatus.CANT)
                        .or(member.massage.eq(MassageStatus.APPLIED))
                )
                .set(member.massage, MassageStatus.CAN)
                .execute();
    }

    /**
     * 안마의자 신청을 한 학생들을 조회하는 쿼리
     * @return List<MassageStudentsDto> (id, stuNum, memberName)
     * @author 김태민
     */
    @Override
    public List<MassageStudentsDto> findMemberByMassageStatus() {
        return queryFactory
                .from(member)
                .select(Projections.fields(MassageStudentsDto.class,
                        member.id, member.memberName, member.stuNum)
                )
                .where(
                        member.massage.eq(MassageStatus.APPLIED)
                )
                .orderBy(member.stuNum.asc())
                .fetch();
    }

    @Override
    public List<Member> findStuInfoByMemberName(String memberName) {
        return queryFactory.from(member)
                .select(member)
                .where(member.memberName.eq(memberName))
                .orderBy(member.stuNum.asc())
                .fetch();
    }

    @Override
    public List<FindStusDto> findAllStuOfRulePage() {
        List<Member> result = queryFactory.selectFrom(member)
                .orderBy(member.stuNum.asc())
                .fetch();

        return this.makeFindStusResponse(result);
    }

    @Override
    public List<FindStusDto> findStusByClassId(Long classId) {
        List<Member> result = queryFactory.selectFrom(member)
                .where(member.stuNum.like(classId + "%"))
                .orderBy(member.stuNum.asc())
                .fetch();

        return this.makeFindStusResponse(result);
    }

    @Override
    public List<FindStusDto> findStusByMemberName(String memberName) {
        List<Member> result = queryFactory.selectFrom(member)
                .where(member.memberName.like("%" + memberName + "%"))
                .orderBy(member.stuNum.asc())
                .fetch();

        return this.makeFindStusResponse(result);
    }

    private List<FindStusDto> makeFindStusResponse(List<Member> result){
        return result.stream().map(
                        m -> new FindStusDto(
                                m.getId(),
                                m.getMemberName(),
                                m.getStuNum(),
                                m.getRuleViolations().stream()
                                        .map(r -> r.getRule().getBig())
                                        .collect(Collectors.toList())
                                        .stream().distinct()
                                        .collect(Collectors.toList())
                        ))
                .collect(Collectors.toList());
    }
}
