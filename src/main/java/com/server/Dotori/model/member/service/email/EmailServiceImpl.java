package com.server.Dotori.model.member.service.email;

import com.server.Dotori.exception.user.exception.UserAuthenticationAnswerNotMatchingException;
import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.AuthPasswordDto;
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

    /**
     * 회원가입할때 이메일로 인증번호를 전송하는 기능
     * @param emailDto email
     * @return 인증번호
     * @author 노경준
     */
    @Override
    public String authKey(EmailDto emailDto) {
        String key = keyUtil.keyIssuance();
        redisUtil.setDataExpire(key, key, 3 * 60 * 1000L);
        emailSendService.sendEmail(emailDto.getEmail(),key);
        return key;
    }

    /**
     * 회원가입할때 authKey에서 보내준 키가 일치한지 인증하는 기능
     * @param memberEmailKeyDto key
     * @return 입력된 키
     * @author 노경준
     */
    @Override
    public String authCheck(MemberEmailKeyDto memberEmailKeyDto) {
        if (memberEmailKeyDto.getKey().equals(redisUtil.getData(memberEmailKeyDto.getKey()))) { // memberEmailKeyDto Key값과 redis에 등록된 Key와 같다면
            redisUtil.deleteData(memberEmailKeyDto.getKey()); // redis에 등록되어있는 Key를 삭제한다.
            return memberEmailKeyDto.getKey(); // 테스트코드를 작성하기 위한 인증 key 반환
        } else {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
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
