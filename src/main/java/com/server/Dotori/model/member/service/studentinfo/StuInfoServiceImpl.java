package com.server.Dotori.model.member.service.studentinfo;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.StudentInfoDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StuInfoServiceImpl implements StuInfoService {

    private final MemberRepository memberRepository;

    /**
     * 학년반별로 조회한 학생들 List를 List Dto로 변경후 반환하는 서비스로직 (사감쌤, 개발자 사용가능)
     * @param id classId
     * @return List - StudentInfoDto (id, stuNum, username, roles)
     */
    @Override
    public List<StudentInfoDto> getStudentInfo(Long id) {
        List<Member> studentInfo = memberRepository.findStudentInfo(id);

        if (studentInfo.isEmpty()) throw new IllegalArgumentException("해당 반에 해당하는 학생이 없습니다.");

        return ObjectMapperUtils.mapAll(studentInfo, StudentInfoDto.class);
    }
}
