package com.server.Dotori.model.member.service.mypage;

import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final CurrentUserUtil currentUserUtil;
    private final MemberRepository memberRepository;

    @Override
    public GetAboutPointDto getMyProfile() {
        return memberRepository.findProfileByMember(currentUserUtil.getCurrentUser());
    }
}
