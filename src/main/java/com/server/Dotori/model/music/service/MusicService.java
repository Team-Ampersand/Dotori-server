package com.server.Dotori.model.music.service;

import com.server.Dotori.model.music.Music;
import com.server.Dotori.model.music.dto.MusicApplicationDto;

public interface MusicService {

    Music musicApplication(MusicApplicationDto musicApplicationDto);
}
