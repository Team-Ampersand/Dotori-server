package com.server.Dotori.model.member.repository.member;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.dto.SelfStudyStudentsDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<SelfStudyStudentsDto> findBySelfStudyAPLLIED();

    List<SelfStudyStudentsDto> findBySelfStudyCategory(Long id);

    void updateSelfStudyStatus();

    List<GetAboutPointDto> findStudentPoint(Long id);

    GetAboutPointDto findProfileByMember(Member memberEntity);

    List<Member> findStudentInfo(Long id);

    List<Member> findAllStudentInfo();

    void updateUnBanSelfStudy();

    void updateUnBanMassage();
}
