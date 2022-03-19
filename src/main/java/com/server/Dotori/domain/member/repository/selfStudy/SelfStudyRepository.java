package com.server.Dotori.domain.member.repository.selfStudy;

import com.server.Dotori.domain.self_study.SelfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfStudyRepository extends JpaRepository<SelfStudy, Long> {
    void deleteByMemberId(Long id);
}
