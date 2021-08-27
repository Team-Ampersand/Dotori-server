package com.server.Dotori.model.selfstudy.service;

import com.querydsl.core.QueryResults;
import com.server.Dotori.model.selfstudy.dto.SelfStudyStudentsDto;

import java.util.List;

public interface SelfStudyService {

    void requestSelfStudy();

    void cancelSelfStudy();

    List<SelfStudyStudentsDto> getSelfStudyStudents();
}
