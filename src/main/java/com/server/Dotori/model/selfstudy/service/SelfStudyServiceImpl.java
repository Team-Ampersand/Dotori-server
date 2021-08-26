package com.server.Dotori.model.selfstudy.service;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.enumType.SelfStudy;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.server.Dotori.model.member.enumType.SelfStudy.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SelfStudyServiceImpl implements SelfStudyService {

    private final CurrentUserUtil currentUserUtil;

    Integer count = 0;

    @Override
    @Transactional
    public void requestSelfStudy() {
        Member currentUser = currentUserUtil.getCurrentUser();

        if (count <= 50){
            if (currentUser.getSelfStudy() == CAN) {
                currentUser.updateSelfStudy(APPLIED);
                count += 1;
                log.info(String.valueOf(count));
            } else
                throw new IllegalArgumentException("이미 자습을 신청한 학생입니다");
        } else
            throw new IllegalArgumentException("자습신청 인원이 모두 찼습니다.");
    }

    @Override
    public void cancelSelfStudy() {
        Member currentUser = currentUserUtil.getCurrentUser();

        if (currentUser.getSelfStudy() == APPLIED) {
            currentUser.updateSelfStudy(CANT);
            count -= 1;
            log.info(String.valueOf(count));
        } else
            throw new IllegalArgumentException("자습신청을 취소할 수 있는 상태가 아닙니다.");
    }
}
