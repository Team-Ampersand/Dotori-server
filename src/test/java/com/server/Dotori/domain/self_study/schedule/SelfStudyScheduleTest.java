package com.server.Dotori.domain.self_study.schedule;

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
class SelfStudyScheduleTest {


    @Autowired
    private ScheduledTaskHolder scheduledTaskHolder;

    @Test
    @DisplayName("토요일에 모든 음악이 삭제되는 일정이 잘 예약되었는지 확인하는 테스트")
    public void weekdaySelfStudyStatusReset() {
        Set<ScheduledTask> scheduledTasks = scheduledTaskHolder.getScheduledTasks();
        scheduledTasks.forEach(scheduledTask -> scheduledTask.getTask().getRunnable().getClass().getDeclaredMethods());

        long count = scheduledTasks.stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals("0 0 2 ? * MON-FRI") && cronTask.toString().equals("com.server.Dotori.domain.self_study.schedule.SelfStudySchedule.weekdaySelfStudyStatusReset"))
                .count();
        Assertions.assertThat(count).isEqualTo(1L);
    }
}