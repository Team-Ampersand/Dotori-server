package com.server.Dotori.model.selfstudy.repository;

import com.server.Dotori.model.selfstudy.SelfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfStudyRepository extends JpaRepository<SelfStudy, Long>, SelfStudyRepositoryCustom {
}
