package com.server.Dotori.model.member.service.studentinfo;

import com.server.Dotori.model.member.dto.RoleUpdateDto;
import com.server.Dotori.model.member.dto.StuNumUpdateDto;
import com.server.Dotori.model.member.dto.StudentInfoDto;
import com.server.Dotori.model.member.dto.UsernameUpdateDto;

import java.util.List;

public interface StuInfoService {

    List<StudentInfoDto> getStudentInfo(Long id);

    List<StudentInfoDto> getAllStudentInfo();

    void updateRole(RoleUpdateDto roleUpdateDto);

    void updateStuNum(StuNumUpdateDto stuNumUpdateDto);

    void updateUsername(UsernameUpdateDto usernameUpdateDto);
}
