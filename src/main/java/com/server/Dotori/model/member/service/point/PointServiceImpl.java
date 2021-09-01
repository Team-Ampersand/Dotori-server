package com.server.Dotori.model.member.service.point;

import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.point.GetPointDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.dto.point.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void point(PointDto pointDto) {
        Member findReceiverMember = memberRepository.findById(pointDto.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException());

        findReceiverMember.updatePoint(findReceiverMember.getPoint() + pointDto.getPoint());
    }

    @Override
    public List<GetPointDto> getAllStudentPoint(Long id) {
        List<GetPointDto> studentPoint = memberRepository.findStudentPoint(id);

        if (studentPoint.isEmpty()) throw new IllegalArgumentException("해당 반에 해당하는 학생이 없습니다.");

        return studentPoint;
    }
}
