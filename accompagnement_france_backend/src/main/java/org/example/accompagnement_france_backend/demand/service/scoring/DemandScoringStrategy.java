package org.example.accompagnement_france_backend.demand.service.scoring;

import org.example.accompagnement_france_backend.demand.dto.DemandDto;

public interface DemandScoringStrategy {
    int calculateScore(DemandDto request);
    String getStrategyName();
}
