package com.server.Dotori.domain.stu_info.service.Impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.stu_info.dto.*;
import com.server.Dotori.domain.stu_info.service.StuInfoService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.exception.ErrorCode;
import com.server.Dotori.global.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StuInfoServiceImpl implements StuInfoService {

    private final MemberRepository memberRepository;
    private final ObjectMapperUtils objectMapperUtils;

    /**
     * 학년반별로 조회한 학생들 List를 List Dto로 변경후 반환하는 서비스로직 (사감쌤, 개발자, 자치위원 사용가능)
     * @param id classId
     * @exception DotoriException (MEMBER_NOT_FOUND_BY_CLASS) 해당 반에 해당하는 학생들이 없을 때
     * @return List - StudentInfoDto (id, stuNum, username, roles, gender)
     * @author 배태현
     */
    @Override
    public List<StudentInfoDto> getStudentInfo(Long id) {
        List<Member> studentInfo = memberRepository.findStudentInfo(id);

        if (studentInfo.isEmpty()) throw new DotoriException(ErrorCode.MEMBER_NOT_FOUND_BY_CLASS);

        return objectMapperUtils.mapAll(studentInfo, StudentInfoDto.class);
    }

    /**
     * 전체 조회한 학생들 List를 List Dto로 변경후 반환하는 서비스로직 (사감쌤, 개발자, 자치위원 사용가능)
     * @return List - StudentInfoDto (id, stuNum, username, roles, gender)
     * @author 배태현
     */
    @Override
    public List<StudentInfoDto> getAllStudentInfo() {
        List<Member> allStudentInfo = memberRepository.findAllStudentInfo();

        return objectMapperUtils.mapAll(allStudentInfo, StudentInfoDto.class);
    }

    /**
     * 권한을 업데이트하는 서비스로직 (사감쌤, 개발자, 자치위원 사용가능)
     * 권한이 업데이트된 사용자는 로그인을 다시 해야한다. (공지를 해야할듯)
     * @param roleUpdateDto (receiverId, roles)
     * @author 배태현
     */
    @Override
    @Transactional
    public void updateRole(RoleUpdateDto roleUpdateDto) {
        Member member = getMember(roleUpdateDto.getReceiverId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //기존 계정의 권한정보를 가지고온다.
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());

        updatedAuthorities.add(new SimpleGrantedAuthority(roleUpdateDto.getRoles().toString())); //변경시켜줄 권한 추가

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                auth.getPrincipal(), auth.getCredentials(), updatedAuthorities //추가한 권한정보로 다시 Security가 관리할 수 있는 객체 생성
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth); // Security가 관리하고있는 객체를 변경된 대상으로 변경

        member.updateRole(roleUpdateDto.getRoles());
        member.updateRefreshToken(null); //다시 로그인을 시켜야하기 때문에 refreshtoken을 미리 지워둔다.
    }

    /**
     * 학번을 변경시키는 서비스로직 (사감쌤, 개발자, 자치위원 사용가능)
     * @param stuNumUpdateDto (receiverId, stuNum)
     * @exception DotoriException (MEMBER_ALREADY_JOIN_THIS_STUNUM) 해당 학번으로 이미 가입된 유저가 있을 때
     * @author 배태현
     */
    @Override
    @Transactional
    public void updateStuNum(StuNumUpdateDto stuNumUpdateDto) {
        Member findMember = getMember(stuNumUpdateDto.getReceiverId());

        if (memberRepository.existsByStuNum(stuNumUpdateDto.getStuNum()))
            throw new DotoriException(ErrorCode.MEMBER_ALREADY_JOIN_THIS_STUNUM);

        findMember.updateStuNum(stuNumUpdateDto.getStuNum());
    }

    /**
     * 학생의 이름을 변경시키는 서비스로직 (사감쌤, 개발자, 자치위원 사용가능)
     * @param memberNameUpdateDto (receiverId, username)
     * @author 배태현
     */
    @Override
    @Transactional
    public void updateMemberName(MemberNameUpdateDto memberNameUpdateDto) {
        Member findMember = getMember(memberNameUpdateDto.getReceiverId());

        findMember.updateMemberName(memberNameUpdateDto.getMemberName());
    }

    /**
     * 학생의 성별을 변경시키는 서비스 로직 (사감쌤, 개발자, 자치위원 사용가능)
     * @param genderUpdateDto (receiverId, gender)
     * @author 배태현
     */
    @Override
    @Transactional
    public void updateGender(GenderUpdateDto genderUpdateDto) {
        Member member = getMember(genderUpdateDto.getReceiverId());

        member.updateMemberGender(genderUpdateDto.getGender());
    }

    /**
     * 이름으로 학생정보를 조회하는 로직 (사감쌤, 기자위, 개발자 사용가능)
     * @param memberName memberName
     * @exception DotoriException (MEMBER_NOT_FOUND) 검색한 이름로 검색된 학생이 없을 때
     * @return List<StudentInfoDto>
     * @author 배태현
     */
    @Override
    public List<StudentInfoDto> getStuInfoByMemberName(String memberName) {
        List<Member> findMembers = memberRepository.findStuInfoByMemberName(memberName);
        if (findMembers.isEmpty()) throw new DotoriException(ErrorCode.MEMBER_NOT_FOUND);
        return objectMapperUtils.mapAll(findMembers, StudentInfoDto.class);
    }

    /**
     * 회원이 존재하는지 체크하고 회원을 반환해주는 메서드
     * @param receiverId memberId
     * @exception DotoriException (MEMBER_NOT_FOUND) id로 회원을 찾지 못했을 때
     * @return Member Entity
     * @author 배태현
     */
    private Member getMember(Long receiverId) {
        return memberRepository.findById(receiverId)
                .orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
