package com.server.Dotori.model.massage.service;

import com.server.Dotori.model.massage.repository.MassageRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Massage;
import com.server.Dotori.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;

import static com.server.Dotori.model.member.enumType.Massage.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MassageServiceImpl implements MassageService {

    private final MassageRepository massageRepository;
    private final CurrentMemberUtil currentMemberUtil;

    @Override
    public void requestMassage(DayOfWeek dayOfWeek, int hour) {
        if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new IllegalArgumentException("안마의자를 신청 할 수 있는 요일이 아닙니다.");
        if (!(hour >= 20 && hour <= 21)) throw new IllegalArgumentException("안마의자 신청을 하실 수 있는 시간이 아닙니다.");

        long count = massageRepository.count();

        if (count < 5) {
            Member currentMember = currentMemberUtil.getCurrentMember();

            if (currentMember.getMassage() == CAN) {
                massageRepository.save(com.server.Dotori.model.massage.Massage.builder()
                    .member(currentMember)
                    .build()
                );
                currentMember.updateMassage(APPLIED);

                log.info("Current MassageRequest Student Count is {}", count+1);
            } else throw new IllegalArgumentException("안마의자 신청을 이미 하셨습니다.");
        } else throw new IllegalArgumentException("안마의자 신청 가능 인원이 초과 하였습니다");
    }
}
