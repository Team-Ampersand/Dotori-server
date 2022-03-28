package com.server.Dotori.domain.massage.repository;

import com.server.Dotori.domain.massage.Massage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassageRepository extends JpaRepository<Massage, Long> {

    void deleteByMemberId(Long id);
}