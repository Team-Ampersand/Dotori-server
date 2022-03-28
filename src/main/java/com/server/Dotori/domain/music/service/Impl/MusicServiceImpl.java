package com.server.Dotori.domain.music.service.Impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.music.Music;
import com.server.Dotori.domain.music.dto.MusicApplicationDto;
import com.server.Dotori.domain.music.dto.MusicResDto;
import com.server.Dotori.domain.music.repository.MusicRepository;
import com.server.Dotori.domain.music.service.MusicService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static com.server.Dotori.domain.member.enumType.Music.APPLIED;
import static com.server.Dotori.domain.member.enumType.Music.CAN;
import static com.server.Dotori.global.exception.ErrorCode.*;

/**
 * @since 1.0.0
 * @author 배태현
 */
@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;
    private final CurrentMemberUtil currentMemberUtil;

    /**
     * 음악을 신청하는 서비스 로직 (로그인된 유저 사용가능) <br>
     * 금요일, 토요일에는 음악신청 불가능
     * @param musicApplicationDto musicApplicationDto (musicUrl)
     * @param dayOfWeek 현재 요일
     * @exception DotoriException (MUSIC_CANT_REQUEST_DATE) 금요일, 토요일에 음악신청을 했을 때
     * @exception DotoriException (MUSIC_ALREADY) 음악신청 상태가 CAN이 아닐 때
     * @return Music
     * @author 배태현
     */
    @Override
    @Transactional
    public Music musicApplication(MusicApplicationDto musicApplicationDto, DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY) throw new DotoriException(MUSIC_CANT_REQUEST_DATE);

        Member currentMember = currentMemberUtil.getCurrentMember();

        if (currentMember.getMusic() == CAN) {
            Music music = musicRepository.save(musicApplicationDto.saveToEntity(currentMember));
            currentMember.updateMusic(APPLIED);
            return music;

        } else {
            throw new DotoriException(MUSIC_ALREADY);
        }
    }

    /**
     * 신청된 모든 음악을 조회하는 서비스 로직 (로그인된 유저 사용가능) <br>
     * 쿼리 파라미터로 날짜가 넘어왔다면 해당 날짜에 신청된 음악목록을 조회한다.
     * @exception DotoriException (MUSIC_NOT_REQUESTED) 신청된 음악이 없을 때
     * @return List-MusicResDto
     * @author 배태현
     * @param date
     */
    @Override
    public List<MusicResDto> getAllMusic(LocalDate date) {
        List<MusicResDto> musicList = null;

        try {
            if (!date.equals(null)) {
                musicList = musicRepository.findDateMusic(date);
            }
        } catch (NullPointerException e) {
            musicList = musicRepository.findAllMusic();
        }

        if (musicList.isEmpty()) throw new DotoriException(MUSIC_NOT_REQUESTED);
        return musicList;
    }

    /**
     * 신청된 음악을 개별삭제하는 서비스 로직 (기자위, 사감쌤 개발자만 가능)
     * @exception DotoriException (MUSIC_NOT_FOUND) 해당 Id의 음악을 찾을 수 없을 때
     * @param musicId musicId
     * @author 배태현
     */
    @Override
    @Transactional
    public void deleteMusic(Long musicId) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new DotoriException(MUSIC_NOT_FOUND));

        musicRepository.deleteById(music.getId());
        music.getMember().updateMusic(CAN);
    }

    /**
     * 음악신청된 회원의 음악신청 상태를 변경하는 서비스로직 (Scheduled)
     * @author 배태현
     */
    @Override
    @Transactional
    public void updateMemberMusicStatus() {
        musicRepository.updateMusicStatusMemberByMember();
    }

    /**
     * 신청된 음악을 모두 지우는 서비스로직 (Scheduled)
     * @author 배태현
     */
    @Override
    public void monthMusicDeleteAll() {
        musicRepository.deleteAll();
    }
}
