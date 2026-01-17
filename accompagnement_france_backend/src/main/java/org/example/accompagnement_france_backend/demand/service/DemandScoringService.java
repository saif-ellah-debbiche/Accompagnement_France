package org.example.accompagnement_france_backend.demand.service;


import org.example.accompagnement_france_backend.demand.dto.DemandDto;
import org.example.accompagnement_france_backend.demand.service.scoring.DemandScoringStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemandScoringService {
    private final List<DemandScoringStrategy> strategies;
    private static final int SUSPICIOUS_THRESHOLD = 50; // Configure this threshold

    // Spring automatically injects all beans implementing DemandScoringStrategy
    public DemandScoringService(List<DemandScoringStrategy> strategies) {
        this.strategies = strategies;
    }

    public boolean isSuspicious(DemandDto request) {
        int totalScore = 0;
        Map<String, Integer> scoreBreakdown = new HashMap<>();

        for (DemandScoringStrategy strategy : strategies) {
            int score = strategy.calculateScore(request);
            totalScore += score;
            scoreBreakdown.put(strategy.getStrategyName(), score);
        }

        return totalScore >= SUSPICIOUS_THRESHOLD;
    }
}
