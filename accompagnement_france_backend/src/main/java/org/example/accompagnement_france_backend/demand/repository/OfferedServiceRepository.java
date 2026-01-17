package org.example.accompagnement_france_backend.demand.repository;

import org.example.accompagnement_france_backend.demand.entity.OfferedService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferedServiceRepository extends JpaRepository<OfferedService, Long> {
    Optional<OfferedService> findServiceByLabel(String serviceLabel);
}