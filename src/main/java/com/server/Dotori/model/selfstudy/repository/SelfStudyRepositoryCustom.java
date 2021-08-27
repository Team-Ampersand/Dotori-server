package com.server.Dotori.model.selfstudy.repository;

import com.querydsl.core.QueryResults;
import com.server.Dotori.model.selfstudy.dto.SelfStudyStudentsDto;

import java.util.List;

public interface SelfStudyRepositoryCustom {

    List<SelfStudyStudentsDto> findBySelfStudyAPLLIED();
}
