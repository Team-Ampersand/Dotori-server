package com.server.Dotori.domain.massage.service;

import com.server.Dotori.domain.massage.dto.MassageStudentsDto;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface MassageService {
    void requestMassage(DayOfWeek dayOfWeek, int hour, int min);
    void cancelMassage(int hour, int min);
    void updateMassageStatus();
    List<MassageStudentsDto> getMassageStudents();
    Map<String, String> getMassageInfo();
}
