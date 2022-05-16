package com.server.Dotori.domain.self_study.repository;

import com.server.Dotori.domain.self_study.SelfStudyCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface SelfStudyCountRepository extends JpaRepository<SelfStudyCount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SelfStudyCount findSelfStudyCountById(Long id);
}
