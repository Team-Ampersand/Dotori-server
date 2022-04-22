package com.server.Dotori.domain.self_study.repository;

import com.server.Dotori.domain.self_study.SelfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import static javax.persistence.LockModeType.PESSIMISTIC_READ;

@Repository
public interface SelfStudyRepository extends JpaRepository<SelfStudy, Long>, SelfStudyRepositoryCustom {
    void deleteByMemberId(Long id);

    @Lock(PESSIMISTIC_READ)
    long count();
}
