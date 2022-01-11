package com.server.Dotori.model.music.schedule;

import com.server.Dotori.model.music.service.MusicService;
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

//    /**
//     * "토요일 23시 59분"에 음악신청 목록을 자동으로 초기화해주는 Scheduled
//     * @author 배태현
//     */
//    @Scheduled(cron = "0 59 23 ? * SAT")
//    public void saturdayMusicDeleteAll() {
//        musicService.saturdayMusicDeleteAll();
//        log.info("Music Delete All At {}", new Date());
//    }

    /**
     * 매 달 1일 새벽 4시에 음악신청 목록을 초기화해주는 Scheduled
     * @author 배태현
     */
    @Scheduled(cron = "0 0 4 1 1/1 ?")
    public void monthMusicDeleteAll() {
        musicService.monthMusicDeleteAll();
        log.info("Music Delete All At {}", new Date());
    }

    /**
     * "월 ~ 금 자정"에 음악신청 상태를 자동으로 update 해주는 Scheduled
     * @author 배태현
     */
    @Scheduled(cron = "0 0 0 ? * MON-FRI")
    public void weekdayMusicStatusReset() {
        musicService.updateMemberMusicStatus();
        log.info("Student Music Status Updated At {}", new Date());
    }

//    /**
//     * 스케줄을 테스트한 메서드 1분마다 log를 찍어준다
//     * ex @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Sat Oct 16 22:08:00 KST 2021
//     *    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Sat Oct 16 22:09:00 KST 2021
//     *    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Sat Oct 16 22:10:00 KST 2021
//     */
//    @Scheduled(cron = "0 0/1 * 1/1 * ?")
//    public void schedulerTest() {
//        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ {}", new Date());
//    }

}
