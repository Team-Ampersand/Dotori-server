package com.server.Dotori.domain.self_study.service;

import com.server.Dotori.domain.self_study.dto.SelfStudyStudentsDto;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface SelfStudyService {

    void requestSelfStudy(DayOfWeek dayOfWeek, int hour);

    void cancelSelfStudy(DayOfWeek dayOfWeek, int hour);

    List<SelfStudyStudentsDto> getSelfStudyStudentByMemberName(String memberName);

    List<SelfStudyStudentsDto> getSelfStudyStudentsByCreateDate();

    List<SelfStudyStudentsDto> getSelfStudyStudentsByCategory(Long id);

    void updateSelfStudyStatus();

    Map<String, String> selfStudyInfo();

    void banSelfStudy(Long id);

    void cancelBanSelfStudy(Long id);
}
