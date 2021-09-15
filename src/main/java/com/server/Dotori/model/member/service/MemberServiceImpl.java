package com.server.Dotori.model.member.service;

import com.server.Dotori.exception.user.exception.UserAlreadyException;
import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.exception.user.exception.UserPasswordNotMatchingException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.dto.MemberLoginDto;
import com.server.Dotori.model.member.dto.MemberPasswordDto;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import com.server.Dotori.util.CurrentUserUtil;
import com.server.Dotori.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static com.server.Dotori.model.member.enumType.Role.*;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final CurrentUserUtil currentUserUtil;

    /**
     * 회원가입
     * @param memberDto memberDto
     * @return result.getId()
     */
    @Override
    public Long signup(MemberDto memberDto){
        if(memberRepository.findByEmail(memberDto.getEmail()) == null && memberRepository.findByStdNum(memberDto.getStdNum()) == null){
            memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
            Member result = memberRepository.save(memberDto.toEntity());
            return result.getId();
        }else {
            throw new UserAlreadyException();
        }
    }

    /**
     * 로그인
     * @param memberLoginDto memberLoginDto
     * @return
     */
    @Override
    public Map<String,String> signin(MemberLoginDto memberLoginDto) {
        Member findUser = memberRepository.findByEmail(memberLoginDto.getEmail()); // email로 유저정보를 가져옴
        if(findUser == null) throw new UserNotFoundException("유저가 존재하지 않습니다."); // 유저가 존재하는지 체크

        boolean passwordCheck = passwordEncoder.matches(memberLoginDto.getPassword(),findUser.getPassword()); // 비밀번호가 DB에 있는 비밀번호와 입력된 비밀번호가 같은지 체크
        if(!passwordCheck) throw new UserNotFoundException("유저가 존재하지 않습니다."); // 비밀번호가 DB에 있는 비밀번호와 입력된 비밀번호가 같은지 체크

        Map<String,String> map = new HashMap<>(); // Token 을 담을수있는 Hashmap 선언
        String accessToken = jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles()); // 유저정보에서 Username : 유저정보에서 Role (Key : Value)
        String refreshToken = jwtTokenProvider.createRefreshToken();

        redisUtil.deleteData(findUser.getUsername()); // redis에 넣기전 유저정보 삭제
        redisUtil.setDataExpire(findUser.getUsername(),refreshToken,jwtTokenProvider.REFRESH_TOKEN_VALIDATION_SECOND); // redis에 key를 Username Value를 refresh입력

        // map에 username, accessToken, refreshToken 정보 입력
        map.put("username", findUser.getUsername());
        map.put("accessToken","Bearer " + accessToken);
        map.put("refreshToken","Bearer " + refreshToken);

        return map;
    }

    /**
     * 로그되어있을 때 사용가능
     * @param memberPasswordDto memberPasswordDto
     * @return
     */
    @Transactional
    @Override
    public Map<String,String> passwordChange(MemberPasswordDto memberPasswordDto) {
        Member findMember = currentUserUtil.getCurrentUser();
        if(!passwordEncoder.matches(memberPasswordDto.getOldPassword(),findMember.getPassword()))
            throw new UserPasswordNotMatchingException();

        findMember.updatePassword(passwordEncoder.encode(memberPasswordDto.getNewPassword()));

        Map<String,String> map = new HashMap<>();
        map.put(findMember.getUsername(),findMember.getPassword());

        return map;
    }

    @Override
    public void logout() {
        redisUtil.deleteData(currentUserUtil.getCurrentUser().getUsername());
    }

}
