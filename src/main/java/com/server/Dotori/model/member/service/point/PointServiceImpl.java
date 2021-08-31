package com.server.Dotori.model.member.service.point;

import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.dto.point.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
