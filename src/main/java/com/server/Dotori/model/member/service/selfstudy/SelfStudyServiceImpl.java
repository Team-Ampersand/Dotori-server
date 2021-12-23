package com.server.Dotori.model.member.service.selfstudy;

import com.server.Dotori.exception.selfstudy.exception.*;
import com.server.Dotori.exception.user.exception.UserNotFoundByClassException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.SelfStudyStudentsDto;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.model.member.repository.selfStudy.SelfStudyRepository;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.server.Dotori.model.member.enumType.SelfStudy.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SelfStudyServiceImpl implements SelfStudyService {

    private final CurrentUserUtil currentUserUtil;
    private final MemberRepository memberRepository;
    private final SelfStudyRepository selfStudyRepository;

    /**
     * 자습 신청 서비스로직 (로그인 된 유저 사용가능) <br>
     * 50명까지 신청 가능, 자습신청 상태가 '가능'인 사람만 신청가능 <br>
     * 금요일, 토요일, 일요일에는 자습신청 불가능 <br>
     * 위 요일을 제외한 나머지 요일에는 오후 8시부터 오후 10시까지만 자습신청 가능 <br>
     * 자습신청 할 시 '신청함'으로 상태변경 <br>
     * @param dayOfWeek 현재 요일
     * @param hour 현재 시
     * @exception SelfStudyCantRequestDateException 금요일, 토요일, 일요일에 자습신청을 했을 때
     * @exception SelfStudyCantRequestTimeException 오후 8시에서 오후 10시 사이가 아닌 시간에 자습신청을 했을 때
     * @exception SelfStudyCantAppliedException 자습신청 상태가 CAN(가능)이 아닐 때 (자습신청을 할 수 없는 상태)
     * @exception SelfStudyOverPersonalException 자습신청 인원이 50명이 넘었을 때
     * @author 배태현
     */
    @Override
    @Transactional
    public void requestSelfStudy(DayOfWeek dayOfWeek, int hour) {
//        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new SelfStudyCantRequestDateException();
//        if (!(hour >= 20 && hour < 22)) throw new SelfStudyCantRequestTimeException(); // 20시(8시)부터 22시(10시 (9시 59분)) 사이가 아니라면 자습신청 불가능

        Member currentUser = currentUserUtil.getCurrentUser();
        long count = selfStudyRepository.count();

        if (count < 50){
            if (currentUser.getSelfStudy() == CAN) {
                currentUser.updateSelfStudy(APPLIED);

                selfStudyRepository.save(com.server.Dotori.model.member.SelfStudy.builder()
                        .member(currentUser)
                        .build());

                log.info("Current Self Study Student Count is {}", count+1);
            } else
                throw new SelfStudyCantAppliedException();
        } else
            throw new SelfStudyOverPersonalException();
    }

    /**
     * 자습신청 서비스 로직 (로그인 된 유저 사용가능) <br>
     * 금요일, 토요일, 일요일에는 자습신청 취소 불가능 <br>
     * 위 요일을 제외한 나머지 요일에는 오후 8시부터 오후 10시까지만 자습신청 취소 가능 <br>
     * 자습신청을 취소할 시 그 날 자습신청 불가능
     * @param dayOfWeek 현재 요일
     * @param hour 현재 시
     * @exception SelfStudyCantCancelDateException 금요일, 토요일, 일요일에 자습신청 취소를 했을 때
     * @exception SelfStudyCantCancelTimeException 오후 8시에서 오후 10시 사이가 아닌 시간에 자습신청 취소를 했을 때
     * @exception SelfStudyCantChangeException 자습신청 상태가 APPLIED(신청됨)가 아닐 때 (자습신청 취소를 할 수 없는 상태)
     * @author 배태현
     */
    @Override
    @Transactional
    public void cancelSelfStudy(DayOfWeek dayOfWeek, int hour) {
//        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new SelfStudyCantCancelDateException();
//        if (!(hour >= 20 && hour < 22)) throw new SelfStudyCantCancelTimeException(); // 20시(8시)부터 22시(10시 (9시 59분)) 사이가 아니라면 자습신청 취소 불가능

        Member currentUser = currentUserUtil.getCurrentUser();
        long count = selfStudyRepository.count();

        if (currentUser.getSelfStudy() == APPLIED) {
            currentUser.updateSelfStudy(CANT);
            selfStudyRepository.deleteByMemberId(currentUser.getId());
            log.info("Current Self Study Student Count is {}", count-1);
        } else
            throw new SelfStudyCantChangeException();
    }

    /**
     * 자습신청한 학생을 전체 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @return List - SelfStudyStudentDto (id, stuNum, username)
     * @exception SelfStudyNotFoundException 자습신청한 학생이 없을 때
     * @author 배태현
     */
    @Override
    public List<SelfStudyStudentsDto> getSelfStudyStudents() {
        List<SelfStudyStudentsDto> selfStudyAPLLIED = memberRepository.findBySelfStudyAPLLIED();

        if (selfStudyAPLLIED.isEmpty()) throw new SelfStudyNotFoundException();
        return selfStudyAPLLIED;
    }

    /**
     * 자습신청한 학생을 학년반별로 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @param id classId
     * @return List - SelfStudyStudentDto (id, stuNum, username)
     * @exception UserNotFoundByClassException 해당 반에 해당하는 학생이 없을 때
     * @author 배태현
     */
    @Override
    public List<SelfStudyStudentsDto> getSelfStudyStudentsByCategory(Long id) {
        List<SelfStudyStudentsDto> selfStudyCategory = memberRepository.findBySelfStudyCategory(id);

        if (selfStudyCategory.isEmpty()) throw new UserNotFoundByClassException();

        return selfStudyCategory;
    }

    /**
     * 자습신청 상태를 '신청가능'으로 변경하고, 자습신청 카운트 초기화 서비스로직 (Schedule)
     * @author 배태현
     */
    @Override
    @Transactional
    public void updateSelfStudyStatus() {
        selfStudyRepository.deleteAll();
        memberRepository.updateSelfStudyStatus();
    }

    /**
     * 자습신청한 학생수와 현재 자신의 자습신청 상태를 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @return Map<String, String> selfStudy_status, count
     * @author 배태현
     */
    @Override
    public Map<String, String> selfStudyInfo() {
        Map<String,String> map = new HashMap<>();
        map.put("selfStudy_status", currentUserUtil.getCurrentUser().getSelfStudy().toString());
        map.put("count", String.valueOf(selfStudyRepository.count()));
        return map;
    }
}
