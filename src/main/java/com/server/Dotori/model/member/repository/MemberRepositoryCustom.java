package com.server.Dotori.model.member.repository;

import com.server.Dotori.model.member.dto.selfstudy.SelfStudyStudentsDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<SelfStudyStudentsDto> findBySelfStudyAPLLIED();

    void updateSelfStudyStatus();
}
