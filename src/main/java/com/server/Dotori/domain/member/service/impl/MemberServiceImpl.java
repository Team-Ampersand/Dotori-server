package com.server.Dotori.domain.member.service.impl;

import com.server.Dotori.domain.member.EmailCertificate;
import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.dto.*;
import com.server.Dotori.domain.member.repository.email.EmailCertificateRepository;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.member.service.MemberService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.exception.ErrorCode;
import com.server.Dotori.global.security.jwt.JwtTokenProvider;
import com.server.Dotori.global.util.CurrentMemberUtil;
import com.server.Dotori.global.util.EmailSender;
import com.server.Dotori.global.util.KeyUtil;
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
     * @return memberId
     * @author 노경준
     */
    @Override
    public Long signup(MemberDto memberDto){
        String stuNum = memberDto.getStuNum();
        String password = memberDto.getPassword();
        String email = memberDto.getEmail();

        try {
            if(!emailCertificateRepository.existsByEmail(email)){
                if (!memberRepository.existsByEmailAndStuNum(email, stuNum)) {
                    Member result = memberRepository.save(
                            memberDto.toEntity(passwordEncoder.encode(password))
                    );
                    return result.getId();
                } else throw new DotoriException(ErrorCode.MEMBER_ALREADY);
            } else throw new DotoriException(ErrorCode.MEMBER_EMAIL_HAS_NOT_BEEN_CERTIFICATE);
        } catch (DataIntegrityViolationException e) {
            throw new DotoriException(ErrorCode.MEMBER_ALREADY);
        }
    }

    /**
     * 회원가입시에 필요한 이메일 인증 서비스 로직
     * @param emailDto email
     * @return key
     * @author 노경준
     */
    @Override
    public String sendEmailSignup(EmailDto emailDto) {
        String email = emailDto.getEmail();

        if(memberRepository.existsByEmail(email)) throw new DotoriException(ErrorCode.MEMBER_ALREADY);
        return sendKeyByEmail(email);
    }

    /**
     * 회원가입시에 필요한 이메일 인증 키를 확인하는 서비스로직
     * @param signUpEmailCheckDto key
     * @author 노경준
     */
    @Override
    public void checkEmailSignup(SignUpEmailCheckDto signUpEmailCheckDto) {
        String key = signUpEmailCheckDto.getKey();

        EmailCertificate emailCertificate = emailCertificateRepository.findByKey(key)
                .orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_AUTHENTICATION_KEY_NOT_MATCHING));

        checkEmail(emailCertificate, key);
    }

    /**
     * 로그인하는 서비스 로직
     * @param signInDto email, password
     * @return token - email, accessToken, refreshToken
     * @author 노경준
     */
    @Transactional
    @Override
    public SignInResponseDto signIn(SignInDto signInDto) {
        Member findMember = memberRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_NOT_FOUND));

        passwordVerify(signInDto.getPassword(), findMember.getPassword());

        SignInResponseDto signInResponseDto = new SignInResponseDto(createToken(findMember),findMember.getGender());

        return signInResponseDto;
    }

    /**
     * 로그인 되어있을 때 비밀번호 변경하는 서비스 로직
     * @param changePasswordDto oldPassword, newPassword
     * @return map - username
     * @author 노경준
     */
    @Transactional
    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) {
        Member findMember = currentMemberUtil.getCurrentMember();

        passwordVerify(changePasswordDto.getCurrentPassword(),findMember.getPassword());

        String encodePassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
        findMember.updatePassword(encodePassword);

        return encodePassword;
    }

    /**
     * 비밀번호 변경시에 필요한 이메일 인증 서비스 로직
     * @param emailDto email
     * @return key
     * @author 노경준
     */
    @Override
    public String sendEmailChangePassword(EmailDto emailDto) {
        String email = emailDto.getEmail();

        memberRepository.findByEmail(email)
                .orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_NOT_FOUND));

        return sendKeyByEmail(email);
    }

    /**
     * 비밀번호 변경시에 필요한 이메일 인증 키를 확인하는 서비스 로직
     * @param changePasswordEmailCheckDto key, newPassword
     * @author 노경준
     */
    @Override
    @Transactional
    public void checkEmailChangePassword(ChangePasswordEmailCheckDto changePasswordEmailCheckDto) {
        EmailCertificate emailCertificate = emailCertificateRepository.findByKey(changePasswordEmailCheckDto.getKey())
                .orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_AUTHENTICATION_KEY_NOT_MATCHING));

        String key = emailCertificate.getKey();
        String email = emailCertificate.getEmail();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_NOT_FOUND));

        if(checkEmail(emailCertificate, key)) member.updatePassword(passwordEncoder.encode(changePasswordEmailCheckDto.getNewPassword()));
    }

    /**
     * 로그아웃 하는 서비스 로직
     * @author 노경준
     */
    @Transactional
    @Override
    public void logout() {
        currentMemberUtil.getCurrentMember().updateRefreshToken(null);
    }

    /**
     * 회원탈퇴 하는 서비스 로직
     * @param withdrawlDto email, password
     * @author 노경준
     */
    @Override
    public void withdrawal(WithdrawlDto withdrawlDto) {
        Member findMember = memberRepository.findByEmail(withdrawlDto.getEmail())
                .orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_NOT_FOUND));

        String entityPassword = findMember.getPassword();

        passwordVerify(withdrawlDto.getPassword(),entityPassword);

        memberRepository.delete(findMember);
    }

    /**
     * 성별을 설정하는 서비스 로직
     * @param setGenderDto email, gender
     * @author 노경준
     */
    @Transactional
    @Override
    public void setGender(SetGenderDto setGenderDto) {
        Member member = memberRepository.findByEmail(setGenderDto.getEmail())
                .orElseThrow(() -> new DotoriException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateMemberGender(setGenderDto.getGender());
    }

    /**
     * 토큰을 생성하는 private 메서드
     * @param member
     * @return map - email, accessToken, refreshToken
     * @author 노경준
     */
    private Map<String,String> createToken(Member member) {
        Map<String,String> map = new HashMap<>();

        String accessToken = jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        member.updateRefreshToken(refreshToken);

        map.put("email", member.getEmail());
        map.put("accessToken","Bearer " + accessToken);
        map.put("refreshToken","Bearer " + refreshToken);

        return map;
    }

    /**
     * 회원가입, 비밀번호를 찾을때 이메일을 전송하는 private 메서드
     * @param - email
     * @return key
     * @author 노경준
     */
    private String sendKeyByEmail(String email){
        String key = keyUtil.keyIssuance();

        EmailCertificateDto emailCertificateDto = new EmailCertificateDto();
        EmailCertificate emailCertificate = emailCertificateDto.toEntity(email, key);

        emailCertificateRepository.deleteEmailCertificateByEmail(email);
        emailCertificateRepository.save(emailCertificate);
        emailSender.send(email,key);

        return key;
    }

    /**
     * 회원가입, 비밀번호를 찾을때 이메일로 전송된 인증키를 체크(검증)하는 private 메서드
     * @param - emailCertificate, key
     * @author 노경준
     */
    private boolean checkEmail(EmailCertificate emailCertificate, String key){
        if(emailCertificate.getExpiredTime().isAfter(LocalDateTime.now())){
            emailCertificateRepository.deleteEmailCertificateByKey(key);
            return true;
        }else{
            emailCertificateRepository.deleteEmailCertificateByKey(key);
            throw new DotoriException(ErrorCode.MEMBER_OVER_CERTIFICATE_TIME);
        }
    }

    /**
     * 요청된 비밀번호와 encode 된 db의 비밀번호가 일치하는지 검증하는 private 메서드
     * @param - requestPassword, entityPassword
     * @author 노경준
     */
    private void passwordVerify(String requestPassword, String entityPassword){
        if(!passwordEncoder.matches(requestPassword, entityPassword)) throw new DotoriException(ErrorCode.MEMBER_PASSWORD_NOT_MATCHING);
    }

}
