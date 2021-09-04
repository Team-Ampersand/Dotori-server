package com.server.Dotori.model.member.schedule;

import com.server.Dotori.model.member.service.selfstudy.SelfStudyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SelfStudySchedule {

    private final SelfStudyService selfStudyService;

    @Scheduled(cron = "0 0 2 ? * MON-FRI", zone = "GMT+9")
    public void weekdaySelfStudyStatusReset() {
        selfStudyService.updateSelfStudyStatus();
    }
}
