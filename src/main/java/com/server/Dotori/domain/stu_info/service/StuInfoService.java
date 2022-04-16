package com.server.Dotori.domain.stu_info.service;

import com.server.Dotori.domain.stu_info.dto.*;

import java.util.List;

public interface StuInfoService {

    List<StudentInfoDto> getStudentInfo(Long id);

    List<StudentInfoDto> getAllStudentInfo();

    void updateRole(RoleUpdateDto roleUpdateDto);

    void updateStuNum(StuNumUpdateDto stuNumUpdateDto);

    void updateMemberName(MemberNameUpdateDto memberNameUpdateDto);

    void updateGender(GenderUpdateDto genderUpdateDto);

    List<StudentInfoDto> getStuInfoByMemberName(String memberName);
}
