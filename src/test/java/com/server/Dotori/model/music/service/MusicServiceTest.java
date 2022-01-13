package com.server.Dotori.model.music.service;

import com.server.Dotori.exception.music.exception.MusicCantRequestDateException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import com.server.Dotori.model.member.repository.member.MemberRepository;
import com.server.Dotori.model.music.Music;
import com.server.Dotori.model.music.dto.MusicApplicationDto;
import com.server.Dotori.model.music.dto.MusicResDto;
import com.server.Dotori.model.music.repository.MusicRepository;
import com.server.Dotori.util.CurrentMemberUtil;
import org.junit.jupiter.api.*;
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

import static com.server.Dotori.model.member.enumType.Music.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MusicServiceTest {

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
                .stdNum("2409")
                .password("0809")
                .email("s20032@gsm.hs.kr")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberRepository.save(memberDto.toEntity());
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
        assertThat(music.getMember().getMusic()).isEqualTo(APPLIED);
        assertThat(music.getUrl()).isEqualTo("https://www.youtube.com/watch?v=6h9qmKWK6Io");
    }

    @Disabled
    @Test
    @DisplayName("음악을 신청할 수 없는 요일에 신청했을 때 예외가 터지나요?")
    public void musicApplicationExceptionTest() {
        assertThrows(
                MusicCantRequestDateException.class,
                () -> musicService.musicApplication(
                        MusicApplicationDto.builder()
                                .musicUrl("https://www.youtube.com/watch?v=6h9qmKWK6Io")
                                .build(),
                        DayOfWeek.FRIDAY // 금요일
                )
        );
    }

    @Test
    @DisplayName("신청된 모든 음악 목록이 잘 조회되는지 확인하는 테스트")
    public void getAllMusicTest() {
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
        List<MusicResDto> getAllMusic = musicService.getAllMusic();

        //then
        assertEquals(getAllMusic.size(), 30);
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
    @DisplayName("회원들의 음악신청여부가 제대로 업데이트 되는지 확인하는 테스트")
    public void memberMusicStatusTest() {
        //given
        memberRepository.save(
                Member.builder()
                        .memberName("qoxogus1")
                        .stdNum("2410")
                        .password("1234")
                        .email("s20033@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(APPLIED)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .memberName("qwer")
                        .stdNum("2408")
                        .password("1234")
                        .email("s20031@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(APPLIED)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .memberName("rewq")
                        .stdNum("2407")
                        .password("1234")
                        .email("s20030@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .build()
        );

        //when
        musicService.updateMemberMusicStatus();

        em.flush();
        em.clear();

        //then
        assertEquals(CAN, memberRepository.findByEmail("s20033@gsm.hs.kr").get().getMusic());
        assertEquals(CAN, memberRepository.findByEmail("s20031@gsm.hs.kr").get().getMusic());
        assertEquals(CAN, memberRepository.findByEmail("s20030@gsm.hs.kr").get().getMusic()); //이 회원은 그대로 CAN
    }

    @Test
    @DisplayName("오늘 신청된 음악이 잘 조회 되나요 ?")
    public void findCurrentDateMusic() {
        //given //when
        String localDate = LocalDate.now().toString();

        Music music = musicService.musicApplication(
                MusicApplicationDto.builder()
                        .musicUrl("https://www.youtube.com/watch?v=6h9qmKWK6Io")
                        .build(),
                DayOfWeek.MONDAY // 월요일
        );

        //then
        List<MusicResDto> currentDateMusic = musicService.getCurrentDateMusic();
        assertEquals(localDate, currentDateMusic.get(0).getCreatedDate().toString().substring(0, 10));
    }

    @Test
    @DisplayName("해당 날짜에 신청된 음악이 잘 조회되나요 ?")
    public void findDateMusic() {
        musicService.musicApplication(
                MusicApplicationDto.builder()
                        .musicUrl("https://www.youtube.com/watch?v=6h9qmKWK6Io")
                        .build(),
                DayOfWeek.MONDAY // 월요일
        );

        LocalDate date = LocalDate.now();

        assertEquals(1, musicService.getDateMusic(date).size());
    }
}