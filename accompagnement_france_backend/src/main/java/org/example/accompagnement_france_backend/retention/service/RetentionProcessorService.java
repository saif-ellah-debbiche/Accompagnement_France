package org.example.accompagnement_france_backend.retention.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.accompagnement_france_backend.demand.entity.Demand;
import org.example.accompagnement_france_backend.demand.repository.DemandRepository;
import org.example.accompagnement_france_backend.retention.entity.RetentionRule;
import org.example.accompagnement_france_backend.retention.enums.RetentionAction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetentionProcessorService {
    private final DemandRepository demandRepository;
    private final RetentionRuleService retentionRuleService;

    private static final int RETENTION_PERIOD_DAYS = 90;

    /**
     * Executes the data retention process.
     * Cron expression "0 0 1 * * ?" means: at 0 seconds, 0 minutes, and 1 hour (1:00 AM), every day.
     * @Scheduled requires @EnableScheduling on the main application class.
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional // Ensures the database operations (updates/saves) are atomic
    public void processExpiredData() {

        log.info("Retention Processor: Starting data cleanup. Policy: Anonymize data older than {} days.", RETENTION_PERIOD_DAYS);
        // 1. Get the active rule dynamically
        RetentionRule activeRule = retentionRuleService.getActiveRule();

        int daysToRetain = activeRule.getDaysToRetainAfterClosure();
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToRetain);
        List<Demand> expiredDemands = demandRepository.findByClosedAtBefore(cutoffDate);
        int processedCount = 0;
        if(activeRule.getActionType().equals(RetentionAction.ANONYMIZE)){
            for (Demand expiredDemand : expiredDemands) {
                // Apply the chosen retention action (Anonymization in this case)
                anonymizeDemand(expiredDemand);
                demandRepository.save(expiredDemand);
                processedCount++;
            }
        }else{
            demandRepository.deleteAll(expiredDemands);
        }


        log.info("Retention Processor: Finished. Total records anonymized: {}", processedCount);
    }

    /**
     * Anonymization implementation: Clears all fields containing Personally Identifiable Information (PII).
     * @param demand The expired Demand object to be modified.
     */
    private void anonymizeDemand(Demand demand) {
        // PII fields are removed or replaced with generic placeholders
        demand.setFirstName("ANONYMIZED");
        demand.setLastName("ANONYMIZED");
        demand.setPhone("0000000000");
        demand.setMessage("DATA_REMOVED_DUE_TO_RETENTION_POLICY");
    }
}
