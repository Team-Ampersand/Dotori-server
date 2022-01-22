package com.server.Dotori.model.member.service.selfstudy;

import com.server.Dotori.model.member.dto.SelfStudyStudentsDto;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface SelfStudyService {

    void requestSelfStudy(DayOfWeek dayOfWeek, int hour);

    void cancelSelfStudy(DayOfWeek dayOfWeek, int hour);

    List<SelfStudyStudentsDto> getSelfStudyStudents();

    List<SelfStudyStudentsDto> getSelfStudyStudentsByCategory(Long id);

    void updateSelfStudyStatus();

    Map<String, String> selfStudyInfo();

    void banSelfStudy(Long id);

    void cancelBanSelfStudy(Long id);
}
