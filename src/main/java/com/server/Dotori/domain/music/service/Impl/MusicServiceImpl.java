package com.server.Dotori.domain.music.service.Impl;

import com.server.Dotori.domain.member.Member;
import com.server.Dotori.domain.member.enumType.MusicStatus;
import com.server.Dotori.domain.music.Music;
import com.server.Dotori.domain.music.dto.MusicApplicationDto;
import com.server.Dotori.domain.music.dto.MusicResDto;
import com.server.Dotori.domain.music.repository.MusicRepository;
import com.server.Dotori.domain.music.service.MusicService;
import com.server.Dotori.global.exception.DotoriException;
import com.server.Dotori.global.exception.ErrorCode;
import com.server.Dotori.global.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

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
     * @exception DotoriException (MUSIC_ALREADY) 음악신청 상태가 CAN이 아닐 때
     * @return Music
     * @author 배태현
     */
    @Override
    @Transactional
    public Music musicApplication(MusicApplicationDto musicApplicationDto, DayOfWeek dayOfWeek) {
        validDayOfWeek(dayOfWeek, ErrorCode.MUSIC_CANT_REQUEST_DATE);

        Member currentMember = currentMemberUtil.getCurrentMember();

        if (isCanApplyMusicStatus()) {
            Music music = musicRepository.save(musicApplicationDto.saveToEntity(currentMember));
            currentMember.updateMusic(MusicStatus.APPLIED);
            return music;

        } else {
            throw new DotoriException(ErrorCode.MUSIC_ALREADY);
        }
    }

    /**
     * 쿼리 파라미터로 넘어온 날짜가를 이용해서 해당 날짜에 신청된 음악목록을 조회하는 서비스 로직 (로그인된 유저 사용가능) <br>
     * @exception DotoriException (MUSIC_NOT_REQUESTED) 신청된 음악이 없을 때
     * @return List-MusicResDto
     * @author 배태현
     * @param date 날짜
     */
    @Override
    public List<MusicResDto> getMusicListByDate(LocalDate date) {
        List<MusicResDto> musicList = musicRepository.findDateMusic(date);

        if (musicList.isEmpty()) throw new DotoriException(ErrorCode.MUSIC_NOT_REQUESTED);
        return musicList;
    }

    /**
     * 신청된 음악을 개별삭제하는 서비스 로직 (기자위, 사감쌤 개발자만 가능)
     * 오늘 신청한 노래를 삭제해야만 음악 상태가 CAN으로 변경된다.
     * @exception DotoriException (MUSIC_NOT_FOUND) 해당 Id의 음악을 찾을 수 없을 때
     * @param musicId musicId
     * @author 배태현
     */
    @Override
    @Transactional
    public void deleteMusic(Long musicId) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new DotoriException(ErrorCode.MUSIC_NOT_FOUND));

        if (isNowDate(music)) {
            music.getMember().updateMusic(MusicStatus.CAN);
        }
        musicRepository.deleteById(music.getId());
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
     * 음악 신청 요청이 들어왔을 때 요일을 검증하는 메서드
     * @param dayOfWeek 요청 들어온 요일
     * @param errorCode musicCantRequestDate ErrorCode
     * @exception DotoriException (MUSIC_CANT_REQUEST_DATE) 금요일, 토요일에 음악신청을 했을 때
     * @author 배태현
     */
    private void validDayOfWeek(DayOfWeek dayOfWeek, ErrorCode errorCode) {
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY) throw new DotoriException(errorCode);
    }

    /**
     * 현재 로그인 된 유저(요청을 보낸 유저)의 음악 신청 상태가 CAN인지 확인해주는 메서드
     * @return boolean
     * @author 배태현
     */
    private boolean isCanApplyMusicStatus() {
        return currentMemberUtil.getCurrentMember().getMusicStatus() == MusicStatus.CAN;
    }

    /**
     * 오늘 신청된 음악인지 판별하는 메서드
     * @param music music entity
     * @return boolean
     * @author 배태현
     */
    private boolean isNowDate(Music music) {
        return LocalDate.now().equals(music.getCreatedDate().toLocalDate());
    }
}
