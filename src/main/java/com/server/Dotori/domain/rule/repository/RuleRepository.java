package com.server.Dotori.domain.rule.repository;

import com.server.Dotori.domain.rule.RuleViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<RuleViolation,Long>,RuleRepositoryCustom {

}
