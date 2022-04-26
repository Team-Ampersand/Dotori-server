package com.server.Dotori.global.config.dev;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.MassageStatus;
import com.server.Dotori.domain.member.enumType.Music;
import com.server.Dotori.domain.member.enumType.SelfStudy;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.server.Dotori.domain.member.enumType.Role.*;
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
        // role account
        Member admin = createAdminAccount();
        Member developer = createDeveloperAccount();
        Member councillor = createCouncillorAccount();

        // 4기 server developer
        Member taehyeon = createServerAccount(4L,"배태현", "3311", "s20032@gsm.hs.kr", Gender.MAN);
        Member taemin = createServerAccount(5L,"김태민", "3405", "s20014@gsm.hs.kr", Gender.MAN);
        Member kyungjun = createServerAccount(6L,"노경준", "3203", "s20018@gsm.hs.kr", Gender.MAN);

        // 4기 client developer
        createClientAccount(7L,"임창규","3214","s20058@gsm.hs.kr", Gender.MAN);
        createClientAccount(8L,"정태환","3415","s20069@gsm.hs.kr", Gender.MAN);
        createClientAccount(9L,"김기홍","3302","s20005@gsm.hs.kr", Gender.MAN);
        createClientAccount(10L,"권진구","3401","s20004@gsm.hs.kr", Gender.MAN);

        // 5기 server developer
        Member seoungone = createServerAccount(11L,"전승원", "2218","s21034@gsm.hs.kr", Gender.MAN);
        Member jaeyoung = createServerAccount(12L,"조재영", "2116", "s21053@gsm.hs.kr", Gender.MAN);

        // 5기 client developer
        createClientAccount(13L,"유환빈", "2308", "s21067@gsm.hs.kr", Gender.MAN);
        createClientAccount(14L,"손정민", "2215", "s21062@gsm.hs.kr", Gender.MAN);
        createClientAccount(15L,"강경민", "2201", "s21038@gsm.hs.kr", Gender.MAN);

        loggingAccessToken(admin, developer, councillor, taehyeon, taemin, kyungjun, seoungone, jaeyoung);
    }

    private Member createServerAccount(Long id, String name, String stuNum, String email, Gender gender) {
        return memberRepository.save(
                Member.builder()
                        .id(id)
                        .memberName(name)
                        .stuNum(stuNum)
                        .email(email)
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_MEMBER))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massageStatus(MassageStatus.CAN)
                        .gender(gender)
                        .build()
        );
    }

    private Member createClientAccount(Long id, String name, String stuNum, String email, Gender gender) {
        return memberRepository.save(
                Member.builder()
                        .id(id)
                        .memberName(name)
                        .stuNum(stuNum)
                        .email(email)
                        .password(passwordEncoder.encode("string"))
                        .point(0L)
                        .refreshToken(null)
                        .roles(singletonList(ROLE_DEVELOPER))
                        .selfStudy(SelfStudy.CAN)
                        .music(Music.CAN)
                        .massageStatus(MassageStatus.CAN)
                        .gender(gender)
                        .build()
        );
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
                        .massageStatus(MassageStatus.CAN)
                        .gender(Gender.PENDING)
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
                        .massageStatus(MassageStatus.CAN)
                        .gender(Gender.PENDING)
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
                        .massageStatus(MassageStatus.CAN)
                        .gender(Gender.PENDING)
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
