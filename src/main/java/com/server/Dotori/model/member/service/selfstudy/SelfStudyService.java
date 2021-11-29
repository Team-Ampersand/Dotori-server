package com.server.Dotori.model.member.service.selfstudy;

import com.server.Dotori.model.member.dto.SelfStudyStudentsDto;
import com.server.Dotori.model.member.enumType.SelfStudy;

import java.time.DayOfWeek;
import java.util.List;

public interface SelfStudyService {

    void requestSelfStudy(DayOfWeek dayOfWeek, int hour);

    void cancelSelfStudy(DayOfWeek dayOfWeek, int hour);

    List<SelfStudyStudentsDto> getSelfStudyStudents();

    List<SelfStudyStudentsDto> getSelfStudyStudentsByCategory(Long id);

    SelfStudy getCurrentSelfStudyStatus();

    void updateSelfStudyStatus();

    int selfStudyCount();
}
