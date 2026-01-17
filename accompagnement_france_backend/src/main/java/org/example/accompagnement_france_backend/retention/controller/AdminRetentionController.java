package org.example.accompagnement_france_backend.retention.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accompagnement_france_backend.retention.entity.RetentionRule;
import org.example.accompagnement_france_backend.retention.service.RetentionRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/retention")
@RequiredArgsConstructor
public class AdminRetentionController {

    private final RetentionRuleService ruleService;
    @GetMapping("/active")
    public ResponseEntity<RetentionRule> getActiveRetentionRule() {
        return ResponseEntity.ok(ruleService.getActiveRule());
    }

    @PostMapping
    public ResponseEntity<RetentionRule> setRetentionRule(@Valid @RequestBody RetentionRule rule) {
        RetentionRule newActiveRule = ruleService.setAndActivateRule(rule);
        return ResponseEntity.ok(newActiveRule);
    }

}
