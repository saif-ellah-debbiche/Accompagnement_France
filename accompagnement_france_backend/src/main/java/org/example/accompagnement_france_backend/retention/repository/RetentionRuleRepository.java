package org.example.accompagnement_france_backend.retention.repository;

import org.example.accompagnement_france_backend.retention.entity.RetentionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface  RetentionRuleRepository extends JpaRepository<RetentionRule, Long> {
    @Query("SELECT r FROM RetentionRule r WHERE r.isActive = TRUE")
    Optional<RetentionRule> findByActiveRule();
}