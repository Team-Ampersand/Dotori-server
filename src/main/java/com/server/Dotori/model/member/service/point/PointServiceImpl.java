package com.server.Dotori.model.member.service.point;

import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.dto.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final MemberRepository memberRepository;

    /**
     * 상점을 부여하는 서비스 로직 (사감쌤 사용가능)
     * @exception
     * @param pointDto pointDto (receiverId, point)
     * @author 배태현
     */
    @Override
    @Transactional
    public void point(PointDto pointDto) {
        Member findReceiverMember = memberRepository.findById(pointDto.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException());

        findReceiverMember.updatePoint(findReceiverMember.getPoint() + pointDto.getPoint());
    }

    /**
     * 학년반별로 학생 상벌점 목록을 조회하는 서비스로직 (사감쌤 사용가능)
     * @param id classId
     * @return List - GetAboutPointDto (id, stuNum, username, point)
     * @author 배태현
     */
    @Override
    public List<GetAboutPointDto> getAllStudentPoint(Long id) {
        List<GetAboutPointDto> studentPoint = memberRepository.findStudentPoint(id);

        if (studentPoint.isEmpty()) throw new IllegalArgumentException("해당 반에 해당하는 학생이 없습니다.");

        return studentPoint;
    }
}
