package com.server.Dotori.dev_config;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Massage;
import com.server.Dotori.model.member.enumType.Music;
import com.server.Dotori.model.member.enumType.SelfStudy;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.server.Dotori.model.member.enumType.Role.*;
import static java.util.Collections.singletonList;

@Slf4j
@Component
@Profile({"dev"})
@RequiredArgsConstructor
public class GenerateMember {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 개발 편의를 위해 Spring Boot WAS가 띄워질 시점에 회원 생성 후 accessToken을 logging한다.<br>
     * memberName : "사감선생님", "도토리개발자", "기숙사자치위원", "배태현", "김태민", "노경준"<br>
     * role : Admin, Developer, Councillor, Member, Member, Member <br>
     * password : string <br>
     */
    @PostConstruct
    private void createDevMembers() {
        Member admin = createAdminAccount();
        Member developer = createDeveloperAccount();
        Member councillor = createCouncillorAccount();
        Member taehyeon = createTaehyeonAccount();
        Member taemin = createTaeminAccount();
        Member kyungjun = createKyungjunAccount();
        createChanggyuAccount();
        createTaehwanAccount();
        createSihyeonAccount();
        createGihongAccount();
        createJinguAccount();

        loggingAccessToken(admin, developer, councillor, taehyeon, taemin, kyungjun);
    }

    private Member createAdminAccount() {
        return memberRepository.save(
                Member.builder()
                        .id(1L)
                        .memberName("사감선생님")
                        .stuNum("0000")
                        .email("s00000@gsm.hs.kr")
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_ADMIN))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );
    }

    private Member createDeveloperAccount() {
        return memberRepository.save(
                Member.builder()
                        .id(2L)
                        .memberName("도토리개발자")
                        .stuNum("0001")
                        .email("s00001@gsm.hs.kr")
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_DEVELOPER))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );
    }

    private Member createCouncillorAccount() {
        return memberRepository.save(
                Member.builder()
                        .id(3L)
                        .memberName("기숙사자치위원")
                        .stuNum("0002")
                        .email("s00002@gsm.hs.kr")
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_COUNCILLOR))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );
    }

    private Member createTaehyeonAccount() {
        return memberRepository.save(
                MemberDto.builder()
                .memberName("배태현")
                .stuNum("2409")
                .email("s20032@gsm.hs.kr")
                .build().toEntity(passwordEncoder.encode("string"))
        );
    }

    private Member createTaeminAccount() {
        return memberRepository.save(
                MemberDto.builder()
                        .memberName("김태민")
                        .stuNum("2406")
                        .email("s20014@gsm.hs.kr")
                        .build().toEntity(passwordEncoder.encode("string"))
        );
    }

    private Member createKyungjunAccount() {
        return memberRepository.save(
                MemberDto.builder()
                        .memberName("노경준")
                        .stuNum("2206")
                        .email("s20018@gsm.hs.kr")
                        .build().toEntity(passwordEncoder.encode("string"))
        );
    }

    private void createChanggyuAccount() {
        memberRepository.save(
                Member.builder()
                        .id(7L)
                        .memberName("임창규")
                        .stuNum("2215")
                        .email("s20058@gsm.hs.kr")
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_DEVELOPER))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );
    }

    private void createTaehwanAccount() {
        memberRepository.save(
                Member.builder()
                        .id(8L)
                        .memberName("정태환")
                        .stuNum("2415")
                        .email("s20069@gsm.hs.kr")
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_DEVELOPER))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );
    }

    private void createSihyeonAccount() {
        memberRepository.save(
                Member.builder()
                        .id(9L)
                        .memberName("송시현")
                        .stuNum("2212")
                        .email("s20040@gsm.hs.kr")
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_DEVELOPER))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );
    }

    private void createGihongAccount() {
        memberRepository.save(
                Member.builder()
                        .id(10L)
                        .memberName("김기홍")
                        .stuNum("2301")
                        .email("s20005@gsm.hs.kr")
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_DEVELOPER))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );
    }

    private void createJinguAccount() {
        memberRepository.save(
                Member.builder()
                        .id(11L)
                        .memberName("권진구")
                        .stuNum("2402")
                        .email("s20004@gsm.hs.kr")
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_DEVELOPER))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massage(Massage.CAN)
                        .build()
        );
    }

    private void loggingAccessToken(Member...members){
        log.info("======================================== Access Token ========================================");
        for (Member member: members)
            log.info("{} {} : \"Bearer {}\"", member.getRoles(), member.getMemberName(), jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
        log.info("==============================================================================================");
    }
}
