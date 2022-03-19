package com.server.Dotori.domain.stu_info.service;

import com.server.Dotori.domain.stu_info.dto.MemberNameUpdateDto;
import com.server.Dotori.domain.stu_info.dto.RoleUpdateDto;
import com.server.Dotori.domain.stu_info.dto.StuNumUpdateDto;
import com.server.Dotori.domain.stu_info.dto.StudentInfoDto;

import java.util.List;

public interface StuInfoService {

    List<StudentInfoDto> getStudentInfo(Long id);

    List<StudentInfoDto> getAllStudentInfo();

    void updateRole(RoleUpdateDto roleUpdateDto);

    void updateStuNum(StuNumUpdateDto stuNumUpdateDto);

    void updateMemberName(MemberNameUpdateDto memberNameUpdateDto);

    List<StudentInfoDto> getStuInfoByMemberName(String memberName);
}
