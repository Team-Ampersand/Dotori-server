package com.server.Dotori.domain.point.service.Impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.main_page.dto.GetAboutPointDto;
import com.server.Dotori.domain.point.dto.PointDto;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.point.service.PointService;
import com.server.Dotori.global.exception.DotoriException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.server.Dotori.global.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.server.Dotori.global.exception.ErrorCode.MEMBER_NOT_FOUND_BY_CLASS;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final MemberRepository memberRepository;

    /**
     * 상점을 부여하는 서비스 로직 (사감쌤 사용가능)
     * @exception DotoriException (MEMBER_NOT_FOUND_BY_CLASS) 해당 Id의 유저를 찾을 수 없을 때
     * @param pointDto pointDto (receiverId, point)
     * @author 배태현
     */
    @Override
    @Transactional
    public void point(PointDto pointDto) {
        Member findReceiverMember = memberRepository.findById(pointDto.getReceiverId())
                .orElseThrow(() -> new DotoriException(MEMBER_NOT_FOUND));

        findReceiverMember.updatePoint(findReceiverMember.getPoint() + pointDto.getPoint());
    }

    /**
     * 학년반별로 학생 상벌점 목록을 조회하는 서비스로직 (사감쌤 사용가능)
     * @param id classId
     * @exception DotoriException (MEMBER_NOT_FOUND_BY_CLASS) 해당 반에 해당하는 학생들이 없을 때
     * @return List - GetAboutPointDto (id, stuNum, username, point)
     * @author 배태현
     */
    @Override
    public List<GetAboutPointDto> getAllStudentPoint(Long id) {
        List<GetAboutPointDto> studentPoint = memberRepository.findStudentPoint(id);

        if (studentPoint.isEmpty()) throw new DotoriException(MEMBER_NOT_FOUND_BY_CLASS);

        return studentPoint;
    }
}
