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
    private final MemberRepository memberRepository;

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

    @Override
    public List<Music> getAllMusic() {
        return musicRepository.findAll();
    }

    @Override
    public void deleteMusic(Long musicId) {
        musicRepository.deleteById(musicId);
    }
}
