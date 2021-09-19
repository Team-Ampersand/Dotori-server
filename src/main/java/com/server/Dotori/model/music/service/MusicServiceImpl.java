package com.server.Dotori.model.music.service;

import com.server.Dotori.exception.music.exception.MusicAlreadyException;
import com.server.Dotori.exception.music.exception.MusicNotAppliedException;
import com.server.Dotori.exception.music.exception.MusicNotFoundException;
import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.music.Music;
import com.server.Dotori.model.music.dto.MusicApplicationDto;
import com.server.Dotori.model.music.dto.MusicResDto;
import com.server.Dotori.model.music.repository.MusicRepository;
import com.server.Dotori.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @author 배태현
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
            throw new MusicAlreadyException();
        }
    }

    /**
     * 신청된 모든 음악을 조회하는 서비스 로직 (로그인된 유저 사용가능)
     * @exception
     * @return List-MusicResDto
     * @author 배태현
     */
    @Override
    public List<MusicResDto> getAllMusic() {
        List<MusicResDto> allMusic = musicRepository.findAllMusic();

        if (allMusic.isEmpty()) throw new MusicNotAppliedException();
        else return allMusic;
    }

    /**
     * 신청된 음악을 개별삭제하는 서비스 로직 (기자위, 사감쌤 개발자만 가능)
     * @exception 
     * @param musicId musicId
     * @author 배태현
     */
    @Override
    public void deleteMusic(Long musicId) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new MusicNotFoundException());

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
     * 신청된 음악을 모두 지우는 서비스로직 (Scheduled)
     * @author 배태현
     */
    @Override
    public void saturdayMusicDeleteAll() {
        musicRepository.deleteAll();
    }
}
