package com.server.Dotori.model.massage.service;

import com.server.Dotori.exception.massage.exception.*;
import com.server.Dotori.model.massage.dto.MassageStudentsDto;
import com.server.Dotori.model.massage.repository.MassageRepository;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.member.MemberRepository;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MassageServiceImpl implements MassageService {

    private final MassageRepository massageRepository;
    private final CurrentMemberUtil currentMemberUtil;
    private final MemberRepository memberRepository;

    @Override
    public void requestMassage(DayOfWeek dayOfWeek, int hour, int min) {
//        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new MassageCantRequestDateException();
//        if (!(hour >= 20 && hour <= 21)) throw new MassageCantRequestTimeException();
//        if (!(min >= 20)) throw new MassageCantRequestTimeException();

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
    public void cancelMassage(DayOfWeek dayOfWeek, int hour, int min) {
//        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) throw new MassageCantRequestDateException();
//        if (!(hour >= 20 && hour <= 21)) throw new MassageCantRequestTimeException();
//        if (!(min >= 20)) throw new MassageCantRequestTimeException();

        long count = massageRepository.count();
        Member currentMember = currentMemberUtil.getCurrentMember();

        if (currentMember.getMassage() == APPLIED) {
            currentMember.updateMassage(CANT);
            massageRepository.deleteByMemberId(currentMember.getId());

            currentMember.updateMassageExpiredDate(null);
            log.info("Current MassageRequest Student Count is {}", count-1);
        } else throw new MassageNotAppliedStatusException();
    }



    @Override
    public void updateMassageStatus() {
        memberRepository.updateUnBanMassage();
        memberRepository.updateMassageStatusCant();
        massageRepository.deleteAll();
    }

    @Override
    public Map<String, String> getMassageStatusAndCount() {
        Map<String, String> map = new HashMap<>();
        map.put("status",currentMemberUtil.getCurrentMember().getMassage().toString());
        map.put("count", String.valueOf(massageRepository.count()));
        return map;
    }

    @Override
    public List<MassageStudentsDto> getMassageStudents() {
        List<MassageStudentsDto> students = memberRepository.findByMassageStatus();
        if (students.size() == 0) throw new MassageNoTheresException();

        return students;
    }
}
