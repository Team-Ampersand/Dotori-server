package com.server.Dotori.domain.music.schedule;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;

import java.util.Set;

@SpringBootTest
class MusicScheduleTest {

    @Autowired
    private ScheduledTaskHolder scheduledTaskHolder;

    @Test
    @DisplayName("매일 자정에 학생들의 음악신청 상태가 '가능'으로 변경하는 일정이 잘 예약되었는지 확인하는 테스트")
    public void weekdayMusicStatusReset() {
        Set<ScheduledTask> scheduledTasks = scheduledTaskHolder.getScheduledTasks();
        scheduledTasks.forEach(scheduledTask -> scheduledTask.getTask().getRunnable().getClass().getDeclaredMethods());

        long count = scheduledTasks.stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals("0 0 0 ? * MON-FRI") && cronTask.toString().equals("com.server.Dotori.domain.music.schedule.MusicSchedule.weekdayMusicStatusReset"))
                .count();
        Assertions.assertThat(count).isEqualTo(1L);
    }
}