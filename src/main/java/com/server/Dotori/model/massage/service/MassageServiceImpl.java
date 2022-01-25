package com.server.Dotori.model.massage.service;

import com.server.Dotori.exception.massage.exception.MassageAlreadyException;
import com.server.Dotori.exception.massage.exception.MassageCantRequestDateException;
import com.server.Dotori.exception.massage.exception.MassageCantRequestTimeException;
import com.server.Dotori.exception.massage.exception.MassageOverException;
import com.server.Dotori.model.massage.repository.MassageRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.Massage;
import com.server.Dotori.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static com.server.Dotori.model.member.enumType.Massage.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MassageServiceImpl implements MassageService {

    private final MassageRepository massageRepository;
    private final CurrentMemberUtil currentMemberUtil;

    @Override
    public void requestMassage(DayOfWeek dayOfWeek, int hour, int min) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new MassageCantRequestDateException();
        if (!(hour >= 20 && hour <= 21)) throw new MassageCantRequestTimeException();
        if (!(min >= 20)) throw new MassageCantRequestTimeException();

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
            } else throw new MassageAlreadyException();
        } else throw new MassageOverException();
    }

    @Override
    public void updateMassageStatus() {
        Member currentMember = currentMemberUtil.getCurrentMember();

        if (currentMember.getMassageExpiredDate().isBefore(LocalDateTime.now())) {
            currentMember.updateMassage(CAN);
        }
    }
}
