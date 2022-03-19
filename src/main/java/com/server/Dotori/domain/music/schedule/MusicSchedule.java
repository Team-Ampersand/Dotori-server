package com.server.Dotori.domain.music.schedule;

import com.server.Dotori.domain.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class MusicSchedule {

    private final MusicService musicService;

    /**
     * "월 ~ 금 자정"에 음악신청 상태를 자동으로 update 해주는 Scheduled
     * @author 배태현
     */
    @Scheduled(cron = "0 0 0 ? * MON-FRI")
    public void weekdayMusicStatusReset() {
        musicService.updateMemberMusicStatus();
        log.info("Student Music Status Updated At {}", new Date());
    }
}
