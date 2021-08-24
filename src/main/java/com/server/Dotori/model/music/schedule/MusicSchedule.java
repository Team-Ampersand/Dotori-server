package com.server.Dotori.model.music.schedule;

import com.server.Dotori.model.music.repository.MusicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class MusicSchedule {

    private final MusicRepository musicRepository;

    public MusicSchedule(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Scheduled(cron = "0 59 23 ? * SAT", zone = "GMT+9")
    public void saturdayMusicDeleteAll() {
        musicRepository.deleteAll();
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
