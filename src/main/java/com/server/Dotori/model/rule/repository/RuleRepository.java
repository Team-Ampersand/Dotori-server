package com.server.Dotori.model.rule.repository;

import com.server.Dotori.model.rule.RuleViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<RuleViolation,Long>,RuleRepositoryCustom {

}
