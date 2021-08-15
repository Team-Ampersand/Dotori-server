package com.server.Dotori.model.music.service;

import com.server.Dotori.model.music.Music;
import com.server.Dotori.model.music.dto.MusicApplicationDto;

import java.util.List;

public interface MusicService {

    Music musicApplication(MusicApplicationDto musicApplicationDto);

    List<Music> getAllMusic();

    void deleteMusic(Long musicId);
}
