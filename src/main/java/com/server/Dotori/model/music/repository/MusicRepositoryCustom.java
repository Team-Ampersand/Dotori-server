package com.server.Dotori.model.music.repository;

import com.server.Dotori.model.music.dto.MusicResDto;

import java.time.LocalDate;
import java.util.List;

public interface MusicRepositoryCustom {

    void updateMusicStatusMemberByMember();

    List<MusicResDto> findAllMusic();

    List<MusicResDto> findCurrentDateMusic(LocalDate localDate);

    List<MusicResDto> findDateMusic(LocalDate date);
}
