package com.server.Dotori.model.massage.repository;

import com.server.Dotori.model.massage.Massage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MassageRepository extends JpaRepository<Massage, Long> ,MassageRepositoryCustom {
}
