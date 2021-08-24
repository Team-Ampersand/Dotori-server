package com.server.Dotori.model.music.schedule;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MusicScheduleTest {

    @Autowired
    private ScheduledTaskHolder scheduledTaskHolder;

    @Test
    public void testYearlyCronTaskScheduled() {
        Set<ScheduledTask> scheduledTasks = scheduledTaskHolder.getScheduledTasks();
        scheduledTasks.forEach(scheduledTask -> scheduledTask.getTask().getRunnable().getClass().getDeclaredMethods());

        long count = scheduledTasks.stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals("0 59 23 ? * SAT") && cronTask.toString().equals("com.server.Dotori.model.music.schedule.MusicSchedule.saturdayMusicDeleteAll"))
                .count();
        
        Assertions.assertThat(count).isEqualTo(1L);
    }

}