package org.example.accompagnement_france_backend.demand.service.scoring;


import org.example.accompagnement_france_backend.demand.dto.DemandDto;
import org.springframework.stereotype.Component;

@Component
public class HoneypotScoringStrategy implements DemandScoringStrategy{
    @Override
    public int calculateScore(DemandDto request) {
        // If the trap field is filled, it's a bot/spam
        if (request.getWebsite() != null && !request.getWebsite().isEmpty()) {
            return 100; // High score = Suspicious
        }
        return 0; // Not suspicious based on this rule
    }

    @Override
    public String getStrategyName() {
        return "website";
    }
}
