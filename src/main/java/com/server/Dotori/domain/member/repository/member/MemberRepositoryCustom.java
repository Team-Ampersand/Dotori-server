package com.server.Dotori.domain.member.repository.member;

import com.server.Dotori.domain.main_page.dto.GetProfileDto;
import com.server.Dotori.domain.massage.dto.MassageStudentsDto;
import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.point.dto.GetAboutPointDto;
import com.server.Dotori.domain.rule.dto.FindStusDto;

import java.util.List;

public interface MemberRepositoryCustom {

    void updateSelfStudyStatus();

    List<GetAboutPointDto> findStudentPoint(Long id);

    GetProfileDto findProfileByMember(Member memberEntity);

    List<Member> findStudentInfo(Long id);

    List<Member> findAllStudentInfo();

    void updateUnBanSelfStudy();

    void updateMassageStatus();

    List<Member> findStuInfoByMemberName(String memberName);

    List<FindStusDto> findAllStuOfRulePage();

    List<FindStusDto> findStusByClassId(Long classId);

    List<FindStusDto> findStusByMemberName(String memberName);

    void updateSelfStudyCheck();
}
