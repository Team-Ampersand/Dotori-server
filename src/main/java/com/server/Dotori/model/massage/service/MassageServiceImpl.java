package com.server.Dotori.model.massage.service;

import com.server.Dotori.model.massage.dto.MassageStudentsDto;
import com.server.Dotori.model.massage.repository.MassageRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.new_exception.DotoriException;
import com.server.Dotori.new_exception.ErrorCode;
import com.server.Dotori.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.server.Dotori.model.member.enumType.Massage.*;
import static com.server.Dotori.new_exception.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MassageServiceImpl implements MassageService {

    private final MassageRepository massageRepository;
    private final CurrentMemberUtil currentMemberUtil;
    private final MemberRepository memberRepository;

    /**
     * 안마의자를 신청하는 로직
     * 5명이 신청가능, 안마의자 신청 상태가 'CAN'일때만 신청가능, 금토일은 신청 불가능
     * 주중 금토일을 제외한 20시 20분 ~ 21시 사이에 신청가능
     * 안마의자 신청시 상태가 'CAN'에서 'APPLIED'로 변경
     * 안마의자 신청 만료기간을 현재기준으로 +1달을 추가해서 ExpiredTime에 저장
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
    public void requestMassage(DayOfWeek dayOfWeek, int hour, int min) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new DotoriException(MASSAGE_CANT_REQUEST_THIS_DATE);
        if (!(hour >= 20 && hour < 21)) throw new DotoriException(MASSAGE_CANT_REQUEST_THIS_TIME);
        if (!(min >= 20)) throw new DotoriException(MASSAGE_CANT_REQUEST_THIS_TIME);

        long count = massageRepository.count();

        if (count < 5) {
            Member currentMember = currentMemberUtil.getCurrentMember();

            if (currentMember.getMassage() == CAN) {
                massageRepository.save(com.server.Dotori.model.massage.Massage.builder()
                    .member(currentMember)
                    .build()
                );
                currentMember.updateMassage(APPLIED);
                currentMember.updateMassageExpiredDate(LocalDateTime.now().plusMonths(1));

                log.info("Current MassageRequest Student Count is {}", count+1);
            } else throw new DotoriException(MASSAGE_ALREADY);
        } else throw new DotoriException(MASSAGE_OVER);
    }

    /**
     * 안마의자 신청 취소하는 로직
     * 5명이 신청가능, 안마의자 신청 상태가 'APPLIED'일때만 취소가능, 금토일은 신청 불가능
     * 주중 금토일을 제외한 20시 20분 ~ 21시 사이에 취소가능
     * 안마의자 신청 취소시 상태가 "APPLIED"에서 "CANT"로 변경
     * 안마의자 신청을 취소한 학생은 MASSAGE 테이블에서 삭제
     * 안마의자 신청 만료기간을 null로 변경
     * @param dayOfWeek 현재 요일
     * @param hour 현재 시
     * @param min 현재 분
     * @exception DotoriException (MASSAGE_CANT_REQUEST_THIS_DATE) 안마의자 신청을 하실 수 없는 요일입니다.
     * @exception DotoriException (MASSAGE_CANT_REQUEST_THIS_TIME) 안마의자 신청은 오후 8시부터 오후 10시까지만 신청이 가능합니다.
     * @exception DotoriException (MASSAGE_CANT_CANCEL_REQUEST) 안마의자 신청을 취소할 수 있는 상태가 아닙니다.
     * @author 김태민
     */
    @Override
    public void cancelMassage(DayOfWeek dayOfWeek, int hour, int min) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new DotoriException(MASSAGE_CANT_REQUEST_THIS_DATE);
        if (!(hour >= 20 && hour < 21)) throw new DotoriException(MASSAGE_CANT_REQUEST_THIS_TIME);
        if (!(min >= 20)) throw new DotoriException(MASSAGE_CANT_REQUEST_THIS_TIME);

        long count = massageRepository.count();
        Member currentMember = currentMemberUtil.getCurrentMember();

        if (currentMember.getMassage() == APPLIED) {
            currentMember.updateMassage(CANT);
            massageRepository.deleteByMemberId(currentMember.getId());

            currentMember.updateMassageExpiredDate(null);
            log.info("Current MassageRequest Student Count is {}", count-1);
        } else throw new DotoriException(MASSAGE_CANT_CANCEL_REQUEST);
    }

    /**
     * updateUnBanMassage 마지막 안마의자 신청일이 한달지난 학생의 안마의자 신청 상태를 'IMPOSSIBLE'에서 'CAN'으로 변경
     * updateMassageStatusCant 안마의자 신청을 했다가 취소를 한 학생의 상태를 'CAN'으로 변경
     * updateMassageStatusImpossible 안마의자 신청을 한 학생의 상태를 'IMPOSSIBLE'로 변경
     * deleteAll 안마의자를 신청한 학생 명단 테이블을 초기화
     * 해당 쿼리들은 주중 금,토,일을 제외한 새벽 2시에 Scheduler로 동작
     * @author 김태민
     */
    @Override
    public void updateMassageStatus() {
        memberRepository.updateUnBanMassage();
        memberRepository.updateMassageStatusCant();
        memberRepository.updateMassageStatusImpossible();
        massageRepository.deleteAll();
    }

    /**
     * 안마의자 신청을 한 학생수와 현재 자신의 안마의자 신청 상태를 조회하는 로직
     * @return Map <String massageStatus(안마의자 신청 상태), String count(안마의자 신청 카운트)>
     * @author 김태민
     */
    @Override
    public Map<String, String> getMassageStatusAndCount() {
        Map<String, String> map = new HashMap<>();
        map.put("status",currentMemberUtil.getCurrentMember().getMassage().toString());
        map.put("count", String.valueOf(massageRepository.count()));
        return map;
    }

    /**
     * 안마의자 신청을 한 학생 전체를 조회하는 로직
     * @return List<MassageStudentsDto> (id, stuNum, memberName)
     * @author 김태민
     */
    @Override
    public List<MassageStudentsDto> getMassageStudents() {
        List<MassageStudentsDto> students = memberRepository.findByMassageStatus();
        if (students.size() == 0) throw new DotoriException(MASSAGE_ANYONE_NOT_REQUEST);

        return students;
    }
}
