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

        Music music = musicRepository.save(musicApplicationDto.saveToEntity(currentUser));
        currentUser.updateMusic(APPLIED);

        return music;
    }
}
