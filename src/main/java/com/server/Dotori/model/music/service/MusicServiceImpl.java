package com.server.Dotori.model.music.service;

import com.server.Dotori.model.board.Board;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.member.repository.MemberRepository;
import com.server.Dotori.model.music.Music;
import com.server.Dotori.model.music.dto.MusicApplicationDto;
import com.server.Dotori.model.music.repository.MusicRepository;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.server.Dotori.model.member.enumType.Music.*;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;
    private final CurrentUserUtil currentUserUtil;

    /**
     * 음악을 신청하는 서비스 로직 (로그인된 유저 사용가능) <br>
     * 이미 음악을 신청한 학생이라면?
     * @exception
     * @param musicApplicationDto musicApplicationDto (musicUrl)
     * @return Music
     */
    @Override
    @Transactional
    public Music musicApplication(MusicApplicationDto musicApplicationDto) {
        Member currentUser = currentUserUtil.getCurrentUser();

        if (currentUser.getMusic() == CAN) {
            Music music = musicRepository.save(musicApplicationDto.saveToEntity(currentUser));
            currentUser.updateMusic(APPLIED);
            return music;

        } else {
            throw new IllegalArgumentException("이미 음악을 신청하신 회원입니다"); //Exception생성하여 예외처리하기
        }
    }

    /**
     * 신청된 모든 음악을 조회하는 서비스 로직 (로그인된 유저 사용가능)
     * @return List-Music (dto로 반환하도록 변경해야할 것 같습니다 / entity 와 spec 이 동일한 dto)
     */
    @Override
    public List<Music> getAllMusic() {
        return musicRepository.findAll();
    }

    /**
     * 신청된 음악을 개별삭제하는 서비스 로직 (기자위, 사감쌤 개발자만 가능)
     * @param musicId musicId
     */
    @Override
    public void deleteMusic(Long musicId) {
        musicRepository.deleteById(musicId);
    }

    /**
     * 음악신청된 회원의 음악신청 상태를 변경하는 서비스로직 (Scheduled)
     */
    @Override
    @Transactional
    public void updateMemberMusicStatus() {
        musicRepository.updateMusicStatusMemberByMember();
    }
}
