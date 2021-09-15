package com.server.Dotori.model.member.service.studentinfo;

import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.RoleUpdateDto;
import com.server.Dotori.model.member.dto.StuNumUpdateDto;
import com.server.Dotori.model.member.dto.StudentInfoDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import com.server.Dotori.util.ObjectMapperUtils;
import com.server.Dotori.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StuInfoServiceImpl implements StuInfoService {

    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;

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

    /**
     * 권한을 업데이트하는 서비스로직 (사감쌤, 개발자 사용가능)
     * 권한이 업데이트된 사용자는 로그인을 다시 해야한다. (공지를 해야할듯)
     * @param roleUpdateDto (receiverId, roles)
     */
    @Override
    @Transactional
    public void updateRole(RoleUpdateDto roleUpdateDto) {
        Member member = memberRepository.findById(roleUpdateDto.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //기존 계정의 권한정보를 가지고온다.
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());

        updatedAuthorities.add(new SimpleGrantedAuthority(roleUpdateDto.getRoles().toString())); //변경시켜줄 권한 추가

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(), auth.getCredentials(), updatedAuthorities //추가한 권한정보로 다시 Security가 관리할 수 있는 객체 생성
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth); // Security가 관리하고있는 객체를 변경된 대상으로 변경

        member.updateRole(roleUpdateDto.getRoles());
        redisUtil.deleteData(member.getEmail()); //다시 로그인을 시켜야하기 때문에 refreshtoken을 미리 지워둔다.
    }

    /**
     * 학번을 변경시키는 서비스로직 (사감쌤 개발자 사용가능)
     * @param stuNumUpdateDto (receiverId, stuNum)
     */
    @Override
    public void updateStuNum(StuNumUpdateDto stuNumUpdateDto) {
        Member findMember = memberRepository.findById(stuNumUpdateDto.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException());

        findMember.updateStuNum(stuNumUpdateDto.getStuNum());
    }
}
