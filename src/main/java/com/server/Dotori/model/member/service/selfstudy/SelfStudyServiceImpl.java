package com.server.Dotori.model.member.service.selfstudy;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.SelfStudyStudentsDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.server.Dotori.model.member.enumType.SelfStudy.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SelfStudyServiceImpl implements SelfStudyService {

    private final CurrentUserUtil currentUserUtil;
    private final MemberRepository memberRepository;

    int count = 0;

    /**
     * 자습 신청 서비스로직 (로그인 된 유저 사용가능) <br>
     * 50명까지 신청 가능, 자습신청 상태가 '가능'인 사람만 신청가능 <br>
     * 자습신청 할 시 '신청함'으로 상태변경
     * @exception
     * @author 배태현
     */
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

    /**
     * 자습신청 서비스 로직 (로그인 된 유저 사용가능) <br>
     * 자습신청을 취소할 시 그 날 자습신청 불가능
     * @exception
     * @author 배태현
     */
    @Override
    @Transactional
    public void cancelSelfStudy() {
        Member currentUser = currentUserUtil.getCurrentUser();

        if (currentUser.getSelfStudy() == APPLIED) {
            currentUser.updateSelfStudy(CANT);
            count -= 1;
            log.info(String.valueOf(count));
        } else
            throw new IllegalArgumentException("자습신청을 취소할 수 있는 상태가 아닙니다.");
    }

    /**
     * 자습신청한 학생을 전체 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @return List - SelfStudyStudentDto (id, stuNum, username)
     * @exception 
     * @author 배태현
     */
    @Override
    public List<SelfStudyStudentsDto> getSelfStudyStudents() {
        List<SelfStudyStudentsDto> selfStudyAPLLIED = memberRepository.findBySelfStudyAPLLIED();

        if (selfStudyAPLLIED.isEmpty()) throw new IllegalArgumentException("자습신청한 학생이 없습니다.");
        return selfStudyAPLLIED;
    }

    /**
     * 자습신청한 학생을 학년반별로 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @param id classId
     * @return List - SelfStudyStudentDto (id, stuNum, username)
     * @exception
     * @author 배태현
     */
    @Override
    public List<SelfStudyStudentsDto> getSelfStudyStudentsByCategory(Long id) {
        List<SelfStudyStudentsDto> selfStudyCategory = memberRepository.findBySelfStudyCategory(id);

        if (selfStudyCategory.isEmpty()) throw new IllegalArgumentException("해당 반에 해당하는 학생이 없습니다.");

        return selfStudyCategory;
    }

    /**
     * 음악신청 상태를 '신청가능'으로 변경하는 서비스로직 (Schedule)
     * @author 배태현
     */
    @Override
    @Transactional
    public void updateSelfStudyStatus() {
        memberRepository.updateSelfStudyStatus();
    }

    /**
     * 자습신청한 학생수를 조회하는 서비스로직 (로그인된 유저 사용가능)
     * @return count
     * @author 배태현
     */
    @Override
    public int selfStudyCount() {
        return count;
    }
}
