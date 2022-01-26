package com.server.Dotori.model.massage.service;

import com.server.Dotori.model.massage.dto.MassageStudentsDto;

import java.time.DayOfWeek;
import java.util.List;

public interface MassageService {
    void requestMassage(DayOfWeek dayOfWeek, int hour, int min);
    void cancelMassage(DayOfWeek dayOfWeek, int hour, int min);
    void updateMassageStatus();
    List<MassageStudentsDto> getMassageStudents();
}
