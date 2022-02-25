package com.server.Dotori.model.member.repository.member;

import com.server.Dotori.model.massage.dto.MassageStudentsDto;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.dto.SelfStudyStudentsDto;
import com.server.Dotori.model.rule.dto.FindStusDto;

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

    void updateMassageStatusCant();

    void updateMassageStatusImpossible();

    List<MassageStudentsDto> findByMassageStatus();

    List<Member> findStuInfoByMemberName(String memberName);

    List<FindStusDto> findAllStuOfRulePage();

    List<FindStusDto> findStusByClassId(Long classId);

    List<FindStusDto> findStusByMemberName(String memberName);
}
