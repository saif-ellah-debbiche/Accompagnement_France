package org.example.accompagnement_france_backend.retention.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.accompagnement_france_backend.retention.entity.RetentionRule;
import org.example.accompagnement_france_backend.retention.enums.RetentionAction;
import org.example.accompagnement_france_backend.retention.repository.RetentionRuleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetentionRuleService {
    private final RetentionRuleRepository ruleRepository;
    @Value("${retention.daysToRetainAfterClosure}")
    private int defaultDaysToRetainAfterClosure;
    @Value("${retention.retentionRuleAction}")
    private String defaultRetentionRuleAction;
    public RetentionRule getActiveRule() {
        return ruleRepository.findByActiveRule().orElseGet(() -> {
            // Fallback Rule for safety
            RetentionRule fallback = new RetentionRule();
            fallback.setDaysToRetainAfterClosure(defaultDaysToRetainAfterClosure); // e.g., 90 days
            fallback.setActionType(RetentionAction.valueOf(defaultRetentionRuleAction));
            System.out.println(fallback);
            return fallback;
        });
    }
    @Transactional
    public RetentionRule setAndActivateRule(RetentionRule newRule) {

        // 1. Log the incoming policy change
        log.info("Admin attempting to set new retention policy: Days={}, Action={}",
                newRule.getDaysToRetainAfterClosure(),
                newRule.getActionType());

        // 2. Deactivate the current active rule, if one exists
        ruleRepository.findByActiveRule().ifPresent(oldRule -> {
            oldRule.setActive(false);
            ruleRepository.save(oldRule);
            log.info("Deactivated old retention policy (ID: {}).", oldRule.getId());
        });

        // 3. Set the new rule as active and save it
        newRule.setActive(true);
        // Ensure the rule is saved/updated in the database
        RetentionRule activatedRule = ruleRepository.save(newRule);

        log.info("Activated new retention policy (ID: {}).", activatedRule.getId());

        return activatedRule;
    }

    // Optional: Get all history of rules
    public List<RetentionRule> getAllRules() {
        return ruleRepository.findAll();
    }
}
