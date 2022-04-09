package com.server.Dotori.domain.main_page.service.Impl;

import com.server.Dotori.domain.main_page.dto.GetProfileDto;
import com.server.Dotori.domain.main_page.service.MainPageService;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private final CurrentMemberUtil currentMemberUtil;
    private final MemberRepository memberRepository;

    /**
     * 메인페이지를 조회했을 때 프로필정보를 반환해주는 서비스로직 (로그인된 유저 사용가능)
     * @return GetAboutPointDto (id, username, stNum, gender)
     * @author 배태현, 노경준
     */
    @Override
    public GetProfileDto getMyProfile() {
        return memberRepository.findProfileByMember(currentMemberUtil.getCurrentMember());
    }
}
