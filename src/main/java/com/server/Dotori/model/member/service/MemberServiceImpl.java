package com.server.Dotori.model.member.service;

import com.server.Dotori.exception.user.exception.*;
import com.server.Dotori.model.member.EmailCertificate;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.*;
import com.server.Dotori.model.member.repository.email.EmailCertificateRepository;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import com.server.Dotori.util.CurrentMemberUtil;
import com.server.Dotori.util.EmailSender;
import com.server.Dotori.util.KeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CurrentMemberUtil currentMemberUtil;
    private final KeyUtil keyUtil;
    private final EmailSender emailSender;
    private final EmailCertificateRepository emailCertificateRepository;

    /**
     * 회원가입하는 서비스 로직
     * @param memberDto username, stdNum, password, email, answer
     * @return result.getId()
     * @author 노경준
     */
    @Override
    public Long signup(MemberDto memberDto){
        String email = memberDto.getEmail();
        try {
            if(!emailCertificateRepository.existsByEmail(email)){
                if (!memberRepository.existsByEmailAndStuNum(email, memberDto.getStuNum())) {
                    memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
                    Member result = memberRepository.save(memberDto.toEntity());
                    return result.getId();
                } else throw new UserAlreadyException();
            } else throw new EmailHasNotBeenCertificateException();
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyException();
        }
    }

    /**
     * 로그인하는 서비스 로직
     * @param memberLoginDto email, password
     * @return token - email, accessToken, refreshToken
     * @author 노경준
     */
    @Transactional
    @Override
    public Map<String,String> signin(MemberLoginDto memberLoginDto) {
        Member findMember = memberRepository.findByEmail(memberLoginDto.getEmail()).orElseThrow(() -> new UserNotFoundException()); // email로 유저정보를 가져옴
        if(!passwordEncoder.matches(memberLoginDto.getPassword(),findMember.getPassword())) throw new UserPasswordNotMatchingException(); // 비밀번호가 DB에 있는 비밀번호와 입력된 비밀번호가 같은지 체크

        Map<String, String> token = createToken(findMember);

        return token;
    }

    /**
     * 토큰을 생성하는 private 메서드
     * @param member
     * @return map - email, accessToken, refreshToken
     * @author 노경준
     */
    private Map<String,String> createToken(Member member) {
        Map<String,String> map = new HashMap<>(); // Token 을 담을수있는 Hashmap 선언
        String accessToken = jwtTokenProvider.createToken(member.getEmail(), member.getRoles()); // 유저정보에서 Username : 유저정보에서 Role (Key : Value)
        String refreshToken = jwtTokenProvider.createRefreshToken();

        member.updateRefreshToken(refreshToken);

        // map 에 email, accessToken, refreshToken 정보 put
        map.put("email", member.getEmail());
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
        Member findMember = currentMemberUtil.getCurrentMember();
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

        EmailCertificateDto emailCertificateDto = new EmailCertificateDto();
        EmailCertificate emailCertificate = emailCertificateDto.toEntity(sendAuthKeyForChangePasswordDto.getEmail(), key);

        emailCertificateRepository.deleteEmailCertificateByEmail(email);
        emailCertificateRepository.save(emailCertificate);
        emailSender.send(email,key);
    }

    /**
     * 비밀번호 찾기(변경)를 할때 BeforeLoginPasswordChange 메소드에서 보낸 인증번호가 일치한지 검증하고, 일치 하다면 새로운 비밀번호로 변경
     * @param verifiedAuthKeyAndChangePasswordDto key, newPassword
     * @author 노경준
     */
    @Override
    @Transactional
    public void verifiedAuthKeyAndChangePassword(VerifiedAuthKeyAndChangePasswordDto verifiedAuthKeyAndChangePasswordDto) {
        String dtoKey = verifiedAuthKeyAndChangePasswordDto.getKey();
        EmailCertificate emailCertificate = emailCertificateRepository.findByKey(dtoKey).orElseThrow(UserAuthenticationAnswerNotMatchingException::new);
        String authKey = emailCertificate.getKey();
        String email = emailCertificate.getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

        if(emailCertificate.getExpiredTime().isAfter(LocalDateTime.now())){
            member.updatePassword(passwordEncoder.encode(verifiedAuthKeyAndChangePasswordDto.getNewPassword()));
            emailCertificateRepository.deleteEmailCertificateByKey(authKey);
        } else {
            emailCertificateRepository.deleteEmailCertificateByKey(authKey);
            throw new OverCertificateTimeException();
        }
    }

    /**
     * 로그아웃 하는 서비스 로직
     * @author 노경준
     */
    @Override
    public void logout() {
        currentMemberUtil.getCurrentMember().updateRefreshToken(null);
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

        memberRepository.delete(findMember);
    }

}
