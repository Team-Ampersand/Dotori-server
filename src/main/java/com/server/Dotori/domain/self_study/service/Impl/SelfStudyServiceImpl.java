package com.server.Dotori.domain.self_study.service.Impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.self_study.SelfStudy;
import com.server.Dotori.domain.self_study.dto.SelfStudyStudentsDto;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.member.repository.selfStudy.SelfStudyRepository;
import com.server.Dotori.domain.self_study.service.SelfStudyService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.server.Dotori.domain.member.enumType.SelfStudy.*;
import static com.server.Dotori.global.exception.ErrorCode.*;

@Service
@Slf4j
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
     * @exception DotoriException (SELF_STUDY_CANT_REQUEST_DATE) 금요일, 토요일, 일요일에 자습신청을 했을 때
     * @exception DotoriException (SELF_STUDY_CANT_REQUEST_TIME) 오후 8시에서 오후 9시 사이가 아닌 시간에 자습신청을 했을 때
     * @exception DotoriException (SELF_STUDY_ALREADY) 자습신청 상태가 CAN(가능)이 아닐 때 (자습신청을 할 수 없는 상태)
     * @exception DotoriException (SELF_STUDY_OVER) 자습신청 인원이 50명이 넘었을 때
     * @author 배태현
     */
    @Override
    @Transactional
    public void requestSelfStudy(DayOfWeek dayOfWeek, int hour) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new DotoriException(SELF_STUDY_CANT_REQUEST_DATE);
        if (!(hour >= 20 && hour < 21)) throw new DotoriException(SELF_STUDY_CANT_REQUEST_TIME); // 20시(8시)부터 21시(9시 (8시 59분)) 사이가 아니라면 자습신청 불가능

        Member currentMember = currentMemberUtil.getCurrentMember();
        long count = selfStudyRepository.count();

        if (count < 50){
            if (currentMember.getSelfStudy() == CAN) {
                currentMember.updateSelfStudy(APPLIED);

                selfStudyRepository.save(SelfStudy.builder()
                        .member(currentMember)
                        .build());

                log.info("Current Self Study Student Count is {}", count+1);
            } else
                throw new DotoriException(SELF_STUDY_ALREADY);
        } else
            throw new DotoriException(SELF_STUDY_OVER);
    }

    /**
     * 자습신청 서비스 로직 (로그인 된 유저 사용가능) <br>
     * 금요일, 토요일, 일요일에는 자습신청 취소 불가능 <br>
     * 위 요일을 제외한 나머지 요일에는 오후 8시부터 오후 10시까지만 자습신청 취소 가능 <br>
     * 자습신청을 취소할 시 그 날 자습신청 불가능
     * @param dayOfWeek 현재 요일
     * @param hour 현재 시
     * @exception DotoriException (SELF_STUDY_CANT_CANCEL_DATE) 금요일, 토요일, 일요일에 자습신청 취소를 했을 때
     * @exception DotoriException (SELF_STUDY_CANT_REQUEST_TIME) 오후 8시에서 오후 9시 사이가 아닌 시간에 자습신청 취소를 했을 때
     * @exception DotoriException (SELF_STUDY_CANT_CANCEL) 자습신청 상태가 APPLIED(신청됨)가 아닐 때 (자습신청 취소를 할 수 없는 상태)
     * @author 배태현
     */
    @Override
    @Transactional
    public void cancelSelfStudy(DayOfWeek dayOfWeek, int hour) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new DotoriException(SELF_STUDY_CANT_CANCEL_DATE);
        if (!(hour >= 20 && hour < 21)) throw new DotoriException(SELF_STUDY_CANT_REQUEST_TIME); // 20시(8시)부터 21시(9시 (8시 59분)) 사이가 아니라면 자습신청 취소 불가능

        Member currentMember = currentMemberUtil.getCurrentMember();
        long count = selfStudyRepository.count();

        if (currentMember.getSelfStudy() == APPLIED) {
            currentMember.updateSelfStudy(CANT);
            selfStudyRepository.deleteByMemberId(currentMember.getId());
            log.info("Current Self Study Student Count is {}", count-1);
        } else
            throw new DotoriException(SELF_STUDY_CANT_CANCEL);
    }

    /**
     * 자습신청한 학생을 전체 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @return List - SelfStudyStudentDto (id, stuNum, username)
     * @exception DotoriException (SELF_STUDY_NOT_FOUND) 자습신청한 학생이 없을 때
     * @author 배태현
     */
    @Override
    public List<SelfStudyStudentsDto> getSelfStudyStudents() {
        List<SelfStudyStudentsDto> selfStudyAPLLIED = memberRepository.findBySelfStudyAPLLIED();

        if (selfStudyAPLLIED.isEmpty()) throw new DotoriException(SELF_STUDY_NOT_FOUND);
        return selfStudyAPLLIED;
    }

    /**
     * 자습신청한 학생을 학년반별로 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @param id classId
     * @return List - SelfStudyStudentDto (id, stuNum, username)
     * @exception DotoriException (MEMBER_NOT_FOUND_BY_CLASS) 해당 반에 해당하는 학생이 없을 때
     * @author 배태현
     */
    @Override
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
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new DotoriException(MEMBER_NOT_FOUND));

        findMember.updateSelfStudy(IMPOSSIBLE);
        findMember.updateSelfStudyExpiredDate(LocalDateTime.now().plusDays(7));
    }

    /**
     * 자습신청 금지를 취소시키는 서비스 로직 (사감쌤, 기자위, 개발자 사용가능)
     * @param id memberId
     * @author 배태현
     */
    @Override
    @Transactional
    public void cancelBanSelfStudy(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new DotoriException(MEMBER_NOT_FOUND));

        findMember.updateSelfStudy(CAN);
        findMember.updateSelfStudyExpiredDate(null);
    }
}