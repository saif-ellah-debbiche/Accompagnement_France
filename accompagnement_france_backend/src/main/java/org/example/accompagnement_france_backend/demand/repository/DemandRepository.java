package org.example.accompagnement_france_backend.demand.repository;

import org.example.accompagnement_france_backend.demand.dto.ServiceCount;
import org.example.accompagnement_france_backend.demand.entity.Demand;
import org.example.accompagnement_france_backend.demand.entity.OfferedService;
import org.example.accompagnement_france_backend.demand.enums.DemandStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DemandRepository extends JpaRepository<Demand, UUID> {
    // Used by the Retention Processor Service
    List<Demand> findByCreatedAtBefore(LocalDateTime cutoffDate);


    List<Demand> findByClosedAtBefore(LocalDateTime cutoffDate);

    long count();
    Integer countAllByStatus(DemandStatus demandStatus);

    List<Demand> findAllByStatus(DemandStatus demandStatus);
    long countByCreatedAtAfter(
            Instant fromDate
    );
    long countByCreatedAtBetween(Instant start, Instant end);
    @Query("""
        SELECT d
        FROM Demand d
        WHERE d.status = :status
        AND d.createdAt >= :fromDate
    """)
    List<Demand> findByStatusFromDate(
            @Param("status") DemandStatus status,
            @Param("fromDate") Instant fromDate
    );
    long countByStatusAndCreatedAtAfter(
            DemandStatus status,
            Instant fromDate
    );
    @Query("""
    SELECT new org.example.accompagnement_france_backend.demand.dto.ServiceCount(d.service.label, COUNT(d))
    FROM Demand d
    GROUP BY d.service.label
    ORDER BY COUNT(d) DESC
""")
    List<ServiceCount> findMostPopularServiceLabels(Pageable pageable);




}
