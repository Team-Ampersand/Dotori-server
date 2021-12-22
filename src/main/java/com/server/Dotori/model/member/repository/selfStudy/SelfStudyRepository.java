package com.server.Dotori.model.member.service.selfstudy.repository;

import com.server.Dotori.model.member.SelfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfStudyRepository extends JpaRepository<SelfStudy, Long> {
    void deleteByMemberId(Long id);
}
