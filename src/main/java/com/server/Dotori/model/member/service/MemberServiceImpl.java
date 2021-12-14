package com.server.Dotori.model.member.service;

import com.server.Dotori.exception.user.exception.UserAlreadyException;
import com.server.Dotori.exception.user.exception.UserAuthenticationKeyNotMatchingException;
import com.server.Dotori.exception.user.exception.UserNotFoundException;
import com.server.Dotori.exception.user.exception.UserPasswordNotMatchingException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.*;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import com.server.Dotori.util.CurrentUserUtil;
import com.server.Dotori.util.EmailSender;
import com.server.Dotori.util.KeyUtil;
import com.server.Dotori.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final CurrentUserUtil currentUserUtil;
    private final KeyUtil keyUtil;
    private final EmailSender emailSender;

    private final Long KEY_EXPIRATION_TIME = 1000L * 60 * 30; // 3분

    /**
     * 회원가입하는 서비스 로직
     * @param memberDto username, stdNum, password, email, answer
     * @return result.getId()
     * @author 노경준
     */
    @Override
    public Long signup(MemberDto memberDto){
        try {
            if (!memberRepository.existsByEmailAndStdNum(memberDto.getEmail(), memberDto.getStdNum())) {
                memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
                Member result = memberRepository.save(memberDto.toEntity());
                return result.getId();
            } else throw new UserAlreadyException();
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyException();
        }
    }

    /**
     * 로그인하는 서비스 로직
     * @param memberLoginDto email, password
     * @return map - username, accessToken, refreshToken
     * @author 노경준
     */
    @Override
    public Map<String,String> signin(MemberLoginDto memberLoginDto) {
        Member findUser = memberRepository.findByEmail(memberLoginDto.getEmail()).orElseThrow(() -> new UserNotFoundException()); // email로 유저정보를 가져옴

        if(!passwordEncoder.matches(memberLoginDto.getPassword(),findUser.getPassword())) throw new UserPasswordNotMatchingException(); // 비밀번호가 DB에 있는 비밀번호와 입력된 비밀번호가 같은지 체크

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
     * 로그인 되어있을 때 비밀번호 변경하는 서비스 로직
     * @param memberPasswordDto oldPassword, newPassword
     * @return map - username
     * @author 노경준
     */
    @Transactional
    @Override
    public String passwordChange(MemberPasswordDto memberPasswordDto) {
        Member findMember = currentUserUtil.getCurrentUser();
        if (!passwordEncoder.matches(memberPasswordDto.getCurrentPassword(), findMember.getPassword())) throw new UserPasswordNotMatchingException();
        findMember.updatePassword(passwordEncoder.encode(memberPasswordDto.getNewPassword()));

        return findMember.getPassword();
    }

    /**
     * 로그인 안했을때 비밀번호 찾기 전에 이메일로 인증번호를 보내는 서비스 로직
     * @param sendAuthKeyForChangePasswordDto email
     * @author 노경준
     */
    @Override
    public void sendAuthKeyForChangePassword(SendAuthKeyForChangePasswordDto sendAuthKeyForChangePasswordDto) {
        Member findMember = memberRepository.findByEmail(sendAuthKeyForChangePasswordDto.getEmail()).orElseThrow(() -> new UserNotFoundException());
        String email = findMember.getEmail();
        String key = keyUtil.keyIssuance();
        redisUtil.setDataExpire(email, key, KEY_EXPIRATION_TIME);
        emailSender.send(email,key);
    }

    /**
     * 비밀번호 찾기(변경)를 할때 BeforeLoginPasswordChange 메소드에서 보낸 인증번호가 일치한지 검증하고, 일치 하다면 새로운 비밀번호로 변경
     * @param verifiedAuthKeyAndChangePasswordDto email, key, newPassword
     * @author 노경준
     */
    @Override
    @Transactional
    public void verifiedAuthKeyAndChangePassword(VerifiedAuthKeyAndChangePasswordDto verifiedAuthKeyAndChangePasswordDto) {
        String email = verifiedAuthKeyAndChangePasswordDto.getEmail();
        String authKey = verifiedAuthKeyAndChangePasswordDto.getKey();
        String redisAuthKey = redisUtil.getData(email);
        System.out.println("redisAuthKey = " + redisAuthKey);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

        if(authKey.equals(redisAuthKey)){
            member.updatePassword(passwordEncoder.encode(verifiedAuthKeyAndChangePasswordDto.getNewPassword()));
            redisUtil.deleteData(email);
        } else {
            throw new UserAuthenticationKeyNotMatchingException();
        }
    }

    /**
     * 로그아웃 하는 서비스 로직
     * @author 노경준
     */
    @Override
    public void logout() {
        redisUtil.deleteData(currentUserUtil.getCurrentUser().getUsername());
    }

    /**
     * 회원탈퇴 하는 서비스 로직
     * @param memberDeleteDto email, password
     * @author 노경준
     */
    @Override
    public void delete(MemberDeleteDto memberDeleteDto) {
        Member findMember = memberRepository.findByEmail(memberDeleteDto.getEmail()).orElseThrow(() -> new UserNotFoundException());
        if(!passwordEncoder.matches(memberDeleteDto.getPassword(),findMember.getPassword()))
            throw new UserPasswordNotMatchingException();

        redisUtil.deleteData(currentUserUtil.getCurrentUser().getUsername());
        memberRepository.delete(findMember);
    }

}
