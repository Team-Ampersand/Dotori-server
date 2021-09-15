package com.server.Dotori.model.music.schedule;

import com.server.Dotori.model.music.repository.MusicRepository;
import com.server.Dotori.model.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MusicSchedule {

    private final MusicService musicService;

    /**
     * "토요일 23시 59분"에 음악신청 목록을 자동으로 초기화해주는 Scheduled
     * @author 배태현
     */
    @Scheduled(cron = "0 59 23 ? * SAT", zone = "GMT+9")
    public void saturdayMusicDeleteAll() {
        musicService.saturdayMusicDeleteAll();
    }

    /**
     * "월 ~ 금 자정"에 음악신청 상태를 자동으로 update 해주는 Scheduled
     * @author 배태현
     */
    @Scheduled(cron = "0 0 0 ? * MON-FRI", zone = "GMT+9")
    public void weekdayMusicStatusReset() {
        musicService.updateMemberMusicStatus();
    }

    /**
     * 스케줄을 테스트한 메서드 1분마다 log를 찍어준다
     * ex 12:00:00 - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     *    12:01:00 - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     *    12:02:00 - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     */
//    @Scheduled(cron = "0 0/1 * 1/1 * ?", zone = "GMT+9")
//    public void saturdayMusicDeleteAll() {
//        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//    }

}
