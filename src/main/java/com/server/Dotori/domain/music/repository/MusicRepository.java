package com.server.Dotori.domain.music.repository;

import com.server.Dotori.domain.music.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long>, MusicRepositoryCustom {

}
