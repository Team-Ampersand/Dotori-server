package com.server.Dotori.model.member.service.mainpage;

import com.server.Dotori.model.member.dto.GetAboutPointDto;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private final CurrentMemberUtil currentMemberUtil;
    private final MemberRepository memberRepository;

    /**
     * 메인페이지를 조회했을 때 프로필정보를 반환해주는 서비스로직 (로그인된 유저 사용가능)
     * @return GetAboutPointDto (id, username, stNum, point)
     * @author 배태현
     */

    @Override
    public GetAboutPointDto getMyProfile() {
        return memberRepository.findProfileByMember(currentMemberUtil.getCurrentMember());
    }
}
