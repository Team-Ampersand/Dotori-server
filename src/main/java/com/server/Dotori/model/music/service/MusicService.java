package com.server.Dotori.model.music.service;

import com.server.Dotori.model.music.Music;
import com.server.Dotori.model.music.dto.MusicApplicationDto;
import com.server.Dotori.model.music.dto.MusicResDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface MusicService {

    Music musicApplication(MusicApplicationDto musicApplicationDto, DayOfWeek dayofWeek);

    List<MusicResDto> getAllMusic(LocalDate date);

    void deleteMusic(Long musicId);
  
    void updateMemberMusicStatus();

    void monthMusicDeleteAll();
}
