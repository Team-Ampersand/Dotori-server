package com.server.Dotori.domain.self_study.service.Impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.self_study.SelfStudy;
import com.server.Dotori.domain.self_study.dto.SelfStudyStudentsDto;
import com.server.Dotori.domain.self_study.repository.SelfStudyRepository;
import com.server.Dotori.domain.self_study.service.SelfStudyService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.exception.ErrorCode;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.server.Dotori.domain.member.enumType.SelfStudy.*;
import static com.server.Dotori.global.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SelfStudyServiceImpl implements SelfStudyService {

    private final CurrentMemberUtil currentMemberUtil;
    private final MemberRepository memberRepository;
    private final SelfStudyRepository selfStudyRepository;

    /**
     * 자습 신청 서비스로직 (로그인 된 유저 사용가능) <br>
     * 50명까지 신청 가능, 자습신청 상태가 '가능'인 사람만 신청가능 <br>
     * 금요일, 토요일, 일요일에는 자습신청 불가능 <br>
     * 위 요일을 제외한 나머지 요일에는 오후 8시부터 오후 9시까지만 자습신청 가능 <br>
     * 자습신청 할 시 '신청함'으로 상태변경 <br>
     * @param dayOfWeek 현재 요일
     * @param hour 현재 시
     * @author 배태현
     */
    @Override
    @Transactional
    public synchronized void requestSelfStudy(DayOfWeek dayOfWeek, int hour) {
        validDayOfWeekAndHour(dayOfWeek, hour, SELF_STUDY_CANT_REQUEST_DATE, SELF_STUDY_CANT_REQUEST_TIME);

        Member currentMember = currentMemberUtil.getCurrentMember();
        long count = selfStudyRepository.count(); // 비관적 잠금이 걸린 count query

        isSmallerThanFifty(count); // count가 50 미만인지 checking
        isVerifiedSelfStudy(CAN, SELF_STUDY_ALREADY); // 회원의 자습신청 상태가 CAN인지 checking

        try {
            currentMember.updateSelfStudy(APPLIED);

            selfStudyRepository.save(SelfStudy.builder()
                    .member(currentMember)
                    .build());

        } catch (DataIntegrityViolationException e) { // TODO : 로깅 레벨에서 다시한번 체크 해야함
            throw new DotoriException(SELF_STUDY_ALREADY);
        }
    }

    /**
     * 자습신청 서비스 로직 (로그인 된 유저 사용가능) <br>
     * 금요일, 토요일, 일요일에는 자습신청 취소 불가능 <br>
     * 위 요일을 제외한 나머지 요일에는 오후 8시부터 오후 10시까지만 자습신청 취소 가능 <br>
     * 자습신청을 취소할 시 그 날 자습신청 불가능
     * @param dayOfWeek 현재 요일
     * @param hour 현재 시
     * @author 배태현
     */
    @Override
    @Transactional
    public void cancelSelfStudy(DayOfWeek dayOfWeek, int hour) {
        validDayOfWeekAndHour(dayOfWeek, hour, SELF_STUDY_CANT_CANCEL_DATE, SELF_STUDY_CANT_CANCEL_TIME);

        Member currentMember = currentMemberUtil.getCurrentMember();

        isVerifiedSelfStudy(APPLIED, SELF_STUDY_CANT_CANCEL); // 회원의 자습신청 상태가 APPLIED인지 checking

        currentMember.updateSelfStudy(CANT);
        selfStudyRepository.deleteByMemberId(currentMember.getId());
    }

    /**
     * 자습신청한 학생 이름으로 검색 하는 서비스로직 (로그인된 유저 사용가능)
     * @return List - SelfStudyStudentDto (id, stuNum, username, gender)
     * @exception DotoriException (SELF_STUDY_NOT_FOUND) 검색한 이름으로 자습신청한 학생이 없을 때
     * @author 배태현
     */
    @Override
    @Transactional(readOnly = true)
    public SelfStudyStudentsDto getSelfStudyStudentByMemberName(String memberName) {
        SelfStudyStudentsDto findSelfStudyAppliedStudent = selfStudyRepository.findByMemberName(memberName);

        if (findSelfStudyAppliedStudent == null) throw new DotoriException(SELF_STUDY_NOT_FOUND);

        return findSelfStudyAppliedStudent;
    }

    /**
     * 자습신청 한 학생들 자습신청 한 순서대로 전체조회하는 서비스 로직 (로그인된 유저 사용가능)
     * @return List - SelfStudyStudentDto (id, stuNum, username, gender)
     * @exception DotoriException (SELF_STUDY_NOT_FOUND) 자습신청한 학생이 없을 때
     * @author 배태현
     */
    @Override
    @Transactional(readOnly = true)
    public List<SelfStudyStudentsDto> getSelfStudyStudentsByCreateDate() {
        List<SelfStudyStudentsDto> selfStudyStudents = selfStudyRepository.findByCreateDate();

        if (selfStudyStudents.isEmpty()) throw new DotoriException(SELF_STUDY_NOT_FOUND);
        return selfStudyStudents;
    }

    /**
     * 자습신청한 학생을 학년반별로 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @param id classId
     * @return List - SelfStudyStudentDto (id, stuNum, username, gender)
     * @exception DotoriException (MEMBER_NOT_FOUND_BY_CLASS) 해당 반에 해당하는 학생이 없을 때
     * @author 배태현
     */
    @Override
    @Transactional(readOnly = true)
    public List<SelfStudyStudentsDto> getSelfStudyStudentsByCategory(Long id) {
        List<SelfStudyStudentsDto> selfStudyCategory = memberRepository.findBySelfStudyCategory(id);

        if (selfStudyCategory.isEmpty()) throw new DotoriException(MEMBER_NOT_FOUND_BY_CLASS);
        return selfStudyCategory;
    }

    /**
     * 자습신청 상태를 '신청가능'으로 변경하고, 자습신청 카운트 초기화, 자습신청 금지 만료기간이 되었다면 다시 신청할 수 있는 상태로 변경해주는 서비스로직 (Schedule)
     * @author 배태현
     */
    @Override
    @Transactional
    public void updateSelfStudyStatus() {
        selfStudyRepository.deleteAll();
        memberRepository.updateSelfStudyStatus();
        memberRepository.updateUnBanSelfStudy();
    }

    /**
     * 자습신청한 학생수와 현재 자신의 자습신청 상태를 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @return Map<String, String> selfStudy_status, count
     * @author 배태현
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, String> selfStudyInfo() {
        Map<String,String> map = new HashMap<>();
        map.put("selfStudy_status", currentMemberUtil.getCurrentMember().getSelfStudy().toString());
        map.put("count", String.valueOf(selfStudyRepository.count()));

        return map;
    }

    /**
     * 자습신청을 금지시키는 서비스 로직 (사감쌤, 기자위, 개발자 사용가능)
     * @param id memberId
     * @author 배태현
     */
    @Override
    @Transactional
    public void banSelfStudy(Long id) {
        updateSelfStudyAndExpiredDate(getMember(id), IMPOSSIBLE, LocalDateTime.now().plusDays(7));
    }

    /**
     * 자습신청 금지를 취소시키는 서비스 로직 (사감쌤, 기자위, 개발자 사용가능)
     * @param id memberId
     * @author 배태현
     */
    @Override
    @Transactional
    public void cancelBanSelfStudy(Long id) {
        updateSelfStudyAndExpiredDate(getMember(id), CAN, null);
    }

    /**
     * 자습신청, 자습신청 취소 요청이 들어왔을 때 시간을 검증하는 메서드 <br>
     * 금요일, 토요일, 일요일에는 예외 발생 <br>
     * 위 요일을 제외한 나머지 요일에는 오후 8시부터 오후 9시까지만 자습신청 가능 <br>
     * @param dayOfWeek 요청 들어온 요일
     * @param hour 요청 들어온 시
     * @param selfStudyRequestDateErrorCode dateErrorCode
     * @param selfStudyRequestTimeErrorCode timeErrorCode
     * @exception DotoriException (SELF_STUDY_CANT_REQUEST_DATE) 금요일, 토요일, 일요일에 자습 신청을 했을 때
     * @exception DotoriException (SELF_STUDY_CANT_REQUEST_TIME) 오후 8시에서 오후 9시 사이가 아닌 시간에 자습 신청을 했을 때
     * @exception DotoriException (SELF_STUDY_CANT_CANCEL_DATE) 금요일, 토요일, 일요일에 자습 신청 취소를 했을 때
     * @exception DotoriException (SELF_STUDY_CANT_CANCEL_TIME) 오후 8시에서 오후 9시 사이가 아닌 시간에 자습 신청 취소를 했을 때
     * @author 배태현
     */
    private void validDayOfWeekAndHour(DayOfWeek dayOfWeek, int hour, ErrorCode selfStudyRequestDateErrorCode, ErrorCode selfStudyRequestTimeErrorCode) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            throw new DotoriException(selfStudyRequestDateErrorCode);
        if (!(hour == 20))
            throw new DotoriException(selfStudyRequestTimeErrorCode); // 20시(8시)부터 21시(9시) 사이가 아니라면 자습 신청/자습 신청 취소 불가능
    }

    /**
     * 현재 로그인 된 유저(요청을 보낸 유저)의 자습신청 상태를 확인해주는 메서드
     * @param selfStudy selfStudyStatus
     * @return boolean
     * @exception DotoriException (SELF_STUDY_ALREADY) 자습신청 상태가 CAN(가능)이 아닐 때 (자습신청을 할 수 없는 상태)
     * @exception DotoriException (SELF_STUDY_CANT_CANCEL) 자습신청 상태가 APPLIED(신청됨)이 아닐 때 (자습신청을 취소할 수 없는 상태)
     * @author 배태현
     */
    private boolean isVerifiedSelfStudy(com.server.Dotori.domain.member.enumType.SelfStudy selfStudy, ErrorCode errorCode) {
        if (currentMemberUtil.getCurrentMember().getSelfStudy() != selfStudy) throw new DotoriException(errorCode);
        return true;
    }

    /**
     * 카운트가 50 미만인지 체크해주는 메서드
     * @param count selfstudy count
     * @return boolean
     * @exception DotoriException (SELF_STUDY_OVER) 자습신청 인원이 50명이 넘었을 때
     * @author 배태현
     */
    private boolean isSmallerThanFifty(long count) {
        if (count >= 50) throw new DotoriException(SELF_STUDY_OVER);
        return true;
    }

    /**
     * 해당 id의 회원이 존재하는지 체크 후 Member를 반환해주는 메서드
     * @param id memberId
     * @return Member Entity
     * @exception DotoriException (MEMBER_NOT_FOUND) 해당 id의 회원을 찾을 수 없을 때
     * @author 배태현
     */
    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new DotoriException(MEMBER_NOT_FOUND));
    }

    /**
     * 자습 신청 금지/금지 취소를 시킬 때 selfStudy 상태와 ExpiredDate를 변경해주는 메서드
     * @param findMember findMember
     * @param selfStudy selfStudyStatus
     * @param localDateTime localDateTime
     * @author 배태현
     */
    private void updateSelfStudyAndExpiredDate(Member findMember, com.server.Dotori.domain.member.enumType.SelfStudy selfStudy, LocalDateTime localDateTime) {
        findMember.updateSelfStudy(selfStudy);
        findMember.updateSelfStudyExpiredDate(localDateTime);
    }
}
