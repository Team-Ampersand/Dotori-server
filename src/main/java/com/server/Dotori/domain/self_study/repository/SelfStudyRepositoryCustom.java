package com.server.Dotori.domain.self_study.repository;

import com.server.Dotori.domain.self_study.dto.SelfStudyStudentsDto;

import java.util.List;

public interface SelfStudyRepositoryCustom {

    List<SelfStudyStudentsDto> findByCreateDate();
}
