package com.server.Dotori.model.member.service.email;

import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.EmailDto;
import com.server.Dotori.model.member.dto.MemberEmailKeyDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.util.CurrentUserUtil;
import com.server.Dotori.util.KeyUtil;
import com.server.Dotori.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final KeyUtil keyUtil;
    private final EmailSendService emailSendService;
    private final RedisUtil redisUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserUtil currentUserUtil;

    @Override
    public String authKey(EmailDto emailDto) {
        String key = keyUtil.keyIssuance();
        redisUtil.setDataExpire(key, key, 3 * 60 * 1000L);
        emailSendService.sendEmail(emailDto.getUserEmail(),key);
        return key;
    }

    @Override
    public String authCheck(MemberEmailKeyDto memberEmailKeyDto) {
        if (memberEmailKeyDto.getKey().equals(redisUtil.getData(memberEmailKeyDto.getKey()))) { // memberEmailKeyDto Key값과 redis에 등록된 Key와 같다면
            redisUtil.deleteData(memberEmailKeyDto.getKey()); // redis에 등록되어있는 Key를 삭제한다.
            return memberEmailKeyDto.getKey(); // 테스트코드를 작성하기 위한 인증 key 반환
        } else {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    @Override
    public Member authPassword(EmailDto emailDto) {
        String email = emailDto.getUserEmail();
        String password = getTempPassword();
        emailSendService.sendPasswordEmail(email,password);

        Member findMember = memberRepository.findByEmail(emailDto.getUserEmail());
        if(findMember == null) throw new UserNotFoundException();

        findMember.updatePassword(passwordEncoder.encode(password));

        return findMember;
    }

    // 난수를 호출하는 메소드
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 12; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
