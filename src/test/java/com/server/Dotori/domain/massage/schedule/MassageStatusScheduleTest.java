package com.server.Dotori.domain.massage.schedule;

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
public class MassageStatusScheduleTest {

    @Autowired
    private ScheduledTaskHolder scheduledTaskHolder;

    @Test
    @DisplayName("새벽 2시에 안마의자 신청 상태가 변경되는 스케쥴러가 동작하는지 확인하는 테스트")
    public void everyNightMassageStatusChangeTest() {
        Set<ScheduledTask> scheduledTasks = scheduledTaskHolder.getScheduledTasks();
        scheduledTasks.forEach(scheduledTask -> scheduledTask.getTask().getRunnable().getClass().getDeclaredMethods());

        long count = scheduledTasks.stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals("0 0 2 ? * MON-FRI") && cronTask.toString().equals("com.server.Dotori.domain.massage.schedule.MassageSchedule.updateMassageStatus"))
                .count();
        Assertions.assertThat(count).isEqualTo(1L);
    }
}
