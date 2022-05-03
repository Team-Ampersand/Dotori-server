package com.server.Dotori.domain.music.repository;

import com.server.Dotori.domain.music.dto.MusicResDto;

import java.time.LocalDate;
import java.util.List;

public interface MusicRepositoryCustom {

    void updateMusicStatusMemberByMember();

    List<MusicResDto> findDateMusic(LocalDate date);
}
