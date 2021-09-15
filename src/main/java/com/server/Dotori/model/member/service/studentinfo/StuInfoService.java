package com.server.Dotori.model.member.service.studentinfo;

import com.server.Dotori.model.member.dto.RoleUpdateDto;
import com.server.Dotori.model.member.dto.StuNumUpdateDto;
import com.server.Dotori.model.member.dto.StudentInfoDto;

import java.util.List;

public interface StuInfoService {

    List<StudentInfoDto> getStudentInfo(Long id);

    void updateRole(RoleUpdateDto roleUpdateDto);

    void updateStuNum(StuNumUpdateDto stuNumUpdateDto);
}
