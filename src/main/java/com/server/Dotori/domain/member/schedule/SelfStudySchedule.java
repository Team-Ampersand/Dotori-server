package com.server.Dotori.domain.member.schedule;

import com.server.Dotori.domain.self_study.service.SelfStudyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class SelfStudySchedule {

    private final SelfStudyService selfStudyService;

    /**
     * "월 ~ 금 새벽 2시"에 학생들 자습신청 상태(금지, 신청함, 신청취소)를 자동으로 변경해주고 자습신청 카운트를 초기화 시키는 Schedule
     * @author 배태현
     */
    @Scheduled(cron = "0 0 2 ? * MON-FRI")
    public void weekdaySelfStudyStatusReset() {
        selfStudyService.updateSelfStudyStatus();
        log.info("Student SelfStudy Status Updated At {}", new Date());
    }
}
