package com.server.Dotori.domain.massage.service.Impl;

import com.server.Dotori.domain.massage.Massage;
import com.server.Dotori.domain.massage.MassageCount;
import com.server.Dotori.domain.massage.dto.MassageStudentsDto;
import com.server.Dotori.domain.massage.repository.MassageCountRepository;
import com.server.Dotori.domain.massage.repository.MassageRepository;
import com.server.Dotori.domain.massage.service.MassageService;
import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.enumType.MassageStatus;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.exception.ErrorCode;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MassageServiceImpl implements MassageService {

    private final MassageRepository massageRepository;
    private final CurrentMemberUtil currentMemberUtil;
    private final MemberRepository memberRepository;
    private final MassageCountRepository massageCountRepository;

    /**
     * 안마의자를 신청하는 로직
     * timeValidateRequestMassage(dayOfWeek, hour, min): 안마의자 신청을 할 수 있는 시간과 요일인지 검증
     * countValidate(count): 안마의자를 신청한 학생이 5명 미만인지 검증
     * massageStatusValidate(currentMember, MassageStatus.CAN): 안마의자 신청 상태가 CAN인지 검증
     * 안마의자 신청시 상태가 'CAN'에서 'APPLIED'로 변경
     * @param dayOfWeek 현재 요일
     * @param hour 현재 시
     * @param min 현재 분
     * @exception DotoriException (MASSAGE_CANT_REQUEST_THIS_DATE) 안마의자 신청을 하실 수 없는 요일입니다.
     * @exception DotoriException (MASSAGE_CANT_REQUEST_THIS_TIME) 안마의자 신청은 오후 8시부터 오후 10시까지만 신청이 가능합니다.
     * @exception DotoriException (MASSAGE_ALREADY) 이미 안마의자 신청을 신청하신 회원입니다.
     * @exception DotoriException (MASSAGE_OVER) 안마의자 신청 인원이 5명을 초과 하였습니다.
     * @author 김태민
     */
    @Override
    @Transactional
    public void requestMassage(DayOfWeek dayOfWeek, int hour, int min) {
        timeValidateMassage(dayOfWeek, hour, min);
        MassageCount massageCount = getMassageCount();
        Long count = massageCount.getCount();
        countValidate(count);
        Member currentMember = currentMemberUtil.getCurrentMember();
        massageStatusValidate(currentMember, MassageStatus.CAN);

        try {
            massageRepository.save(Massage.builder()
                    .member(currentMember)
                    .build()
            );
            massageCount.addCount();
        } catch (DataIntegrityViolationException e) {
            throw new DotoriException(ErrorCode.MASSAGE_ALREADY);
        }
        currentMember.updateMassage(MassageStatus.APPLIED);
        log.info("Current MassageRequest Student Count is {}", count);
    }

    /**
     * 안마의자 신청 취소하는 로직
     * timeValidateRequestMassage(hour, min): 안마의자 신청을 할 수 있는 시간과 요일인지 검증
     * massageStatusValidate(currentMember, MassageStatus.APPLIED): 안마의자 신청 상태가 APPLIED인지 검증
     * 안마의자 신청 취소시 상태가 "APPLIED"에서 "CANT"로 변경
     * 안마의자 신청을 취소한 학생은 MASSAGE 테이블에서 삭제
     * @param hour 현재 시
     * @param min 현재 분
     * @exception DotoriException (MASSAGE_CANT_CANCEL_THIS_TIME) 안마의자 신청 취소는 오후 8시부터 오후 10시까지만 신청 취소가 가능합니다.
     * @exception DotoriException (MASSAGE_CANT_CANCEL_REQUEST) 안마의자 신청을 취소할 수 있는 상태가 아닙니다.
     * @author 김태민
     */

    @Override
    @Transactional
    public void cancelMassage(int hour, int min) {
        timeValidateMassage(hour, min);
        Member currentMember = currentMemberUtil.getCurrentMember();
        massageStatusValidate(currentMember, MassageStatus.APPLIED);
        MassageCount massageCount = getMassageCount();
        Long count = massageCount.getCount();

        massageRepository.deleteByMemberId(currentMember.getId());
        currentMember.updateMassage(MassageStatus.CANT);
        massageCount.deductionCount();

        log.info("Current MassageRequest Student Count is {}", count-1L);
    }

    /**
     * updateMassageStatusCant 안마의자 신청을 했다가 취소를 한 학생의 상태를 'CAN'으로 변경
     * deleteAll 안마의자를 신청한 학생 명단 테이블을 초기화
     * 해당 쿼리들은 주중 금,토,일을 제외한 새벽 2시에 Scheduler로 동작
     * @author 김태민
     */
    @Override
    @Transactional
    public void updateMassageStatus() {
        memberRepository.updateMassageStatus();
        massageRepository.deleteAll();
        getMassageCount().clearCount();

    }

    /**
     * 안마의자 신청을 한 학생수와 현재 자신의 안마의자 신청 상태를 조회하는 로직
     * @return Map <String massageStatus(안마의자 신청 상태), String count(안마의자 신청 카운트)>
     * @author 김태민
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getMassageInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("status",currentMemberUtil.getCurrentMember().getMassageStatus().toString());
        map.put("count", String.valueOf(massageRepository.count()));
        return map;
    }

    /**
     * 안마의자 신청을 한 학생 전체를 조회하는 로직
     * @return List<MassageStudentsDto> (id, stuNum, memberName)
     * @author 김태민
     */
    @Override
    @Transactional(readOnly = true)
    public List<MassageStudentsDto> getMassageStudents() {
        List<MassageStudentsDto> students = massageRepository.findByMassageList();
        if (students.size() == 0) throw new DotoriException(ErrorCode.MASSAGE_ANYONE_NOT_REQUEST);

        return students;
    }

    /**
     * 안마의자를 신청할때 시간을 검증하는 로직
     * Overloading
     * @param dayOfWeek 현재 요일
     * @param hour 현재 시
     * @param min 현재 분
     */
    private void timeValidateMassage(DayOfWeek dayOfWeek, int hour, int min) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new DotoriException(ErrorCode.MASSAGE_CANT_REQUEST_THIS_DATE);
        if (!(hour >= 20 && hour < 21)) throw new DotoriException(ErrorCode.MASSAGE_CANT_REQUEST_THIS_TIME);
        if (!(min >= 20)) throw new DotoriException(ErrorCode.MASSAGE_CANT_REQUEST_THIS_TIME);
    }

    /**
     * 안마의자 신청 취소를 할때 시간을 검증하는 로직
     * Overloading
     * 신청을 했다면 요일 검증을 필요없다고 판단하여 시,분만 검증
     * @param hour 현재 시
     * @param min 현재 분
     */
    private void timeValidateMassage(int hour, int min) {
        if (!(hour >= 20 && hour < 21)) throw new DotoriException(ErrorCode.MASSAGE_CANT_CANCEL_THIS_TIME);
        if (!(min >= 20)) throw new DotoriException(ErrorCode.MASSAGE_CANT_CANCEL_THIS_TIME);
    }

    /**
     * 안마의자를 신청한 사람이 5명 미만인지 검증하는 로직
     * @param count 현재 안마의자 신청 회원수
     */
    private void countValidate(Long count) {
        if (count >= 5L) throw new DotoriException(ErrorCode.MASSAGE_OVER);
    }

    /**
     * 안마의자 신청 상태를 검증하는 로직
     * @param currentMember 회원 자신
     * @param massageStatus 해당 상태가 맞는지 검증하는 상태
     */
    private void massageStatusValidate(Member currentMember, MassageStatus massageStatus) {
        if (!currentMember.getMassageStatus().equals(massageStatus)) throw new DotoriException(ErrorCode.MASSAGE_ALREADY);
    }

    /**
     * 안마의자 카운트를 반환해주는 로직 (캡슐화)
     * 정적으로 1L primary key를 가진 MassageCount 엔티티를 반환하는 이유
     * 한개의 컬럼으로 계속 count를 관리하기 떄문에 1L id를 가진 massageCount 컬럼으로 계속 관리하기 위해
     * @return MassageCount
     */
    private MassageCount getMassageCount() {
        return massageCountRepository.findMassageCountById(1L);
    }

}
