package com.server.Dotori.model.member.service.selfstudy;

import com.server.Dotori.model.member.dto.selfstudy.SelfStudyStudentsDto;

import java.util.List;

public interface SelfStudyService {

    void requestSelfStudy();

    void cancelSelfStudy();

    List<SelfStudyStudentsDto> getSelfStudyStudents();

    void updateSelfStudyStatus();

    Integer selfStudyCount();
}
