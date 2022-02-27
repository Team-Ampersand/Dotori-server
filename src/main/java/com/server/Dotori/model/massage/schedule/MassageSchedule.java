package com.server.Dotori.model.massage.schedule;

import com.server.Dotori.model.massage.service.MassageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MassageSchedule {

    private final MassageService massageService;

    /**
     * 주중 금,토,일을 제외한 새벽 2시마다 도는 Scheduler
     */
    @Scheduled(cron = "0 0 2 ? * MON-FRI")
    public void updateMassageStatus() {
        massageService.updateMassageStatus();
    }

}
