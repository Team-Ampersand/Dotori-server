package com.server.Dotori.domain.massage.repository;

import com.server.Dotori.domain.massage.MassageCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface MassageCountRepository extends JpaRepository<MassageCount, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    MassageCount findMassageCountById(Long id);
}
