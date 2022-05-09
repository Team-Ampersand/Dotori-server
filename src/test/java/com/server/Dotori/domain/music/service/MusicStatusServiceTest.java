package com.server.Dotori.domain.music.service;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.dto.MemberDto;
import com.server.Dotori.domain.member.enumType.Gender;
import com.server.Dotori.domain.member.enumType.MusicStatus;
import com.server.Dotori.domain.member.enumType.Role;
import com.server.Dotori.domain.member.enumType.SelfStudyStatus;
import com.server.Dotori.domain.member.repository.member.MemberRepository;
import com.server.Dotori.domain.music.Music;
import com.server.Dotori.domain.music.dto.MusicApplicationDto;
import com.server.Dotori.domain.music.dto.MusicResDto;
import com.server.Dotori.domain.music.repository.MusicRepository;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MusicStatusServiceTest {

    @Autowired private MusicService musicService;
    @Autowired private MusicRepository musicRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CurrentMemberUtil currentMemberUtil;
    @Autowired
    EntityManager em;

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentUser() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .memberName("배태현")
                .stuNum("2409")
                .password("0809")
                .email("s20032@gsm.hs.kr")
                .gender(Gender.MAN)
                .build();
        memberRepository.save(
                memberDto.toEntity(
                        passwordEncoder.encode(memberDto.getPassword())
                )
        );
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getEmail(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentMemberEmail = CurrentMemberUtil.getCurrentEmail();
        assertEquals("s20032@gsm.hs.kr", currentMemberEmail);
    }

    @Test
    @DisplayName("음악신청이 제대로 되는지 확인하는 테스트")
    public void musicApplicationTest() {
        //given //when
        Music music = musicService.musicApplication(
                MusicApplicationDto.builder()
                        .musicUrl("https://www.youtube.com/watch?v=6h9qmKWK6Io")
                        .build(),
                DayOfWeek.MONDAY // 월요일
        );

        //then
        assertThat(music.getMember().getMusicStatus()).isEqualTo(MusicStatus.APPLIED);
        assertThat(music.getUrl()).isEqualTo("https://www.youtube.com/watch?v=6h9qmKWK6Io");
    }

    @Test
    @DisplayName("음악을 신청할 수 없는 요일에 신청했을 때 예외가 터지나요?")
    public void musicApplicationExceptionTest() {
        assertThrows(
                DotoriException.class,
                () -> musicService.musicApplication(
                        MusicApplicationDto.builder()
                                .musicUrl("https://www.youtube.com/watch?v=6h9qmKWK6Io")
                                .build(),
                        DayOfWeek.FRIDAY // 금요일
                )
        );

        assertThrows(
                DotoriException.class,
                () -> musicService.musicApplication(
                        MusicApplicationDto.builder()
                                .musicUrl("https://www.youtube.com/watch?v=6h9qmKWK6Io")
                                .build(),
                        DayOfWeek.SATURDAY // 금요일
                )
        );
    }
    
    @Test
    @DisplayName("음악 신청 시 음악 신청 상태가 CAN이 아닐 때 예외가 잘 터지나요 ?")
    public void musicApplicationMusicStatusException() {
        //given
        Member currentMember = currentMemberUtil.getCurrentMember();

        //when
        currentMember.updateMusic(MusicStatus.APPLIED);

        //then
        assertThrows(
                DotoriException.class,
                () -> musicService.musicApplication(
                        MusicApplicationDto.builder()
                                .musicUrl("https://www.youtube.com/watch?v=6h9qmKWK6Io")
                                .build(),
                        DayOfWeek.MONDAY // 금요일
                )
        );
    }

    @Test
    @DisplayName("신청된 모든 음악 목록이 잘 조회되는지 확인하는 테스트")
    public void getMusicListByDateTest() {
        //given
        Member currentMember = currentMemberUtil.getCurrentMember();

        List<Music> musicList = Stream.generate(
                () -> Music.builder()
                        .url("https://www.youtube.com/watch?v=her_7pa0vrg&pp=sAQA")
                        .member(currentMember)
                        .build()
        ).limit(30).collect(Collectors.toList());

        musicRepository.saveAll(musicList);

        //when
        List<MusicResDto> musicListByDate = musicService.getMusicListByDate(LocalDate.now());

        //then
        assertEquals(musicListByDate.size(), 30);
    }

    @Test
    @DisplayName("음악 목록이 비어있을 때 예외가 잘 터지나요 ?")
    public void getMusicListByDateExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> musicService.getMusicListByDate(LocalDate.now())
        );
    }

    @Test
    @DisplayName("신청된 음악이 삭제되는지 확인하는 테스트")
    public void deleteMusicTest() {
        //given
        Member currentMember = currentMemberUtil.getCurrentMember();

        List<Music> musicList = Stream.generate(
                () -> Music.builder()
                        .url("https://www.youtube.com/watch?v=her_7pa0vrg&pp=sAQA")
                        .member(currentMember)
                        .build()
        ).limit(10).collect(Collectors.toList());

        List<Music> musics = musicRepository.saveAll(musicList);

        //when
        musicService.deleteMusic(musics.get(9).getId());

        //then
        assertEquals(9, musicRepository.findAll().size());
    }

    @Test
    @DisplayName("해당하는 음악을 찾을 수 없을 때 예외가 제대로 터지나요 ?")
    public void musicNotFoundExceptionTest() {
        //when //then
        assertThrows(
                DotoriException.class,
                () -> musicService.deleteMusic(0L)
        );
    }

    @Test
    @DisplayName("회원들의 음악신청여부가 제대로 업데이트 되는지 확인하는 테스트")
    public void memberMusicStatusTest() {
        //given
        memberRepository.save(
                Member.builder()
                        .memberName("qoxogus1")
                        .stuNum("2410")
                        .password("1234")
                        .email("s20033@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .musicStatus(MusicStatus.APPLIED)
                        .selfStudyStatus(SelfStudyStatus.CAN)
                        .point(0L)
                        .gender(Gender.MAN)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .memberName("qwer")
                        .stuNum("2408")
                        .password("1234")
                        .email("s20031@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .musicStatus(MusicStatus.APPLIED)
                        .selfStudyStatus(SelfStudyStatus.CAN)
                        .point(0L)
                        .gender(Gender.MAN)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .memberName("rewq")
                        .stuNum("2407")
                        .password("1234")
                        .email("s20030@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .musicStatus(MusicStatus.CAN)
                        .selfStudyStatus(SelfStudyStatus.CAN)
                        .point(0L)
                        .gender(Gender.MAN)
                        .build()
        );

        //when
        musicService.updateMemberMusicStatus();

        em.flush();
        em.clear();

        //then
        assertEquals(MusicStatus.CAN, memberRepository.findByEmail("s20033@gsm.hs.kr").get().getMusicStatus());
        assertEquals(MusicStatus.CAN, memberRepository.findByEmail("s20031@gsm.hs.kr").get().getMusicStatus());
        assertEquals(MusicStatus.CAN, memberRepository.findByEmail("s20030@gsm.hs.kr").get().getMusicStatus()); //이 회원은 그대로 CAN
    }
}