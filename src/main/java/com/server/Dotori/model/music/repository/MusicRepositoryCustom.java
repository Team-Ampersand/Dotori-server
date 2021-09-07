package com.server.Dotori.model.music.repository;

import com.server.Dotori.model.member.Member;
import com.server.Dotori.model.music.dto.MusicResDto;

import java.util.List;

public interface MusicRepositoryCustom {

    void updateMusicStatusMemberByMember();

    List<MusicResDto> findAllMusic();
}
