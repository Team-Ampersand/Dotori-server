package com.server.Dotori.domain.self_study.repository;

import com.server.Dotori.domain.self_study.SelfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfStudyRepository extends JpaRepository<SelfStudy, Long>, SelfStudyRepositoryCustom {
    void deleteByMemberId(Long id);
}
