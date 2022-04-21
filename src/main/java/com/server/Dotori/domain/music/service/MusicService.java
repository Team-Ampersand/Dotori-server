package com.server.Dotori.domain.music.service;

import com.server.Dotori.domain.music.Music;
import com.server.Dotori.domain.music.dto.MusicApplicationDto;
import com.server.Dotori.domain.music.dto.MusicResDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface MusicService {

    Music musicApplication(MusicApplicationDto musicApplicationDto, DayOfWeek dayofWeek);

    List<MusicResDto> getMusicListByDate(LocalDate date);

    void deleteMusic(Long musicId);
  
    void updateMemberMusicStatus();
}
