package com.server.Dotori.model.music.service;

import com.server.Dotori.model.music.Music;
import com.server.Dotori.model.music.dto.MusicApplicationDto;
import com.server.Dotori.model.music.dto.MusicResDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public interface MusicService {

    Music musicApplication(MusicApplicationDto musicApplicationDto, DayOfWeek dayofWeek);

    List<MusicResDto> getAllMusic();

    List<MusicResDto> getCurrentDateMusic();

    void deleteMusic(Long musicId);
  
    void updateMemberMusicStatus();

    void saturdayMusicDeleteAll();
}
