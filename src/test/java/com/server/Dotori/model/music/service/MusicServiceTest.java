package com.server.Dotori.model.music.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.dto.MemberDto;
import com.server.Dotori.model.member.enumType.Role;
import com.server.Dotori.model.member.enumType.SelfStudy;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.member.service.MemberService;
import com.server.Dotori.model.music.Music;
import com.server.Dotori.model.music.dto.MusicApplicationDto;
import com.server.Dotori.model.music.dto.MusicResDto;
import com.server.Dotori.model.music.repository.MusicRepository;
import com.server.Dotori.util.CurrentUserUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.server.Dotori.model.member.enumType.Music.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Commit
class MusicServiceTest {

    @Autowired private MusicService musicService;
    @Autowired private MusicRepository musicRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CurrentUserUtil currentUserUtil;
    @Autowired private MemberService memberService;
    @Autowired
    EntityManager em;

    @BeforeEach
    @DisplayName("로그인 되어있는 유저를 확인하는 테스트")
    void currentUser() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("배태현")
                .stdNum("2409")
                .password("0809")
                .email("s20032@gsm.hs.kr")
                .answer("배털")
                .build();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberRepository.save(memberDto.toEntity(Role.ROLE_ADMIN));
        System.out.println("======== saved =========");

        // when login session 발급
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberDto.getUsername(),
                memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        System.out.println("=================================");
        System.out.println(context);

        //then
        String currentUsername = CurrentUserUtil.getCurrentUserNickname();
        assertEquals("배태현", currentUsername);
    }

    @Test
    @DisplayName("음악신청이 제대로 되는지 확인하는 테스트")
    public void musicApplicationTest() {
        //given //when
        Music music = musicService.musicApplication(
                MusicApplicationDto.builder()
                        .musicUrl("https://www.youtube.com/watch?v=6h9qmKWK6Io")
                        .build()
        );

        //then
        assertThat(music.getMember().getMusic()).isEqualTo(APPLIED);
        assertThat(music.getUrl()).isEqualTo("https://www.youtube.com/watch?v=6h9qmKWK6Io");
    }

    @Test
    @DisplayName("신청된 모든 음악 목록이 잘 조회되는지 확인하는 테스트")
    public void getAllMusicTest() {
        //given
        Member currentUser = currentUserUtil.getCurrentUser();

        List<Music> musicList = Stream.generate(
                () -> Music.builder()
                        .url("https://www.youtube.com/watch?v=her_7pa0vrg&pp=sAQA")
                        .member(currentUser)
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
        Member currentUser = currentUserUtil.getCurrentUser();

        List<Music> musicList = Stream.generate(
                () -> Music.builder()
                        .url("https://www.youtube.com/watch?v=her_7pa0vrg&pp=sAQA")
                        .member(currentUser)
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
                        .username("qoxogus1")
                        .stdNum("2410")
                        .password("1234")
                        .email("s20033@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(APPLIED)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .answer("배털")
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .username("qwer")
                        .stdNum("2408")
                        .password("1234")
                        .email("s20031@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(APPLIED)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .answer("배털")
                        .build()
        );

        memberRepository.save(
                Member.builder()
                        .username("rewq")
                        .stdNum("2407")
                        .password("1234")
                        .email("s20030@gsm.hs.kr")
                        .roles(Collections.singletonList(Role.ROLE_ADMIN))
                        .music(CAN)
                        .selfStudy(SelfStudy.CAN)
                        .point(0L)
                        .answer("배털")
                        .build()
        );

        //when
        musicService.updateMemberMusicStatus();

        em.flush();
        em.clear();

        //then
        assertEquals(CAN, memberRepository.findByUsername("qoxogus1").getMusic());
        assertEquals(CAN, memberRepository.findByUsername("qwer").getMusic());
        assertEquals(CAN, memberRepository.findByUsername("rewq").getMusic()); //이 회원은 그대로 CAN
    }
}