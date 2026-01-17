package org.example.accompagnement_france_backend.demand.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.accompagnement_france_backend.demand.dto.DemandDto;
import org.example.accompagnement_france_backend.demand.dto.DemandStatics;
import org.example.accompagnement_france_backend.demand.dto.DemandStatusDto;
import org.example.accompagnement_france_backend.demand.entity.Demand;
import org.example.accompagnement_france_backend.demand.enums.DemandStatus;
import org.example.accompagnement_france_backend.demand.service.DemandScoringService;
import org.example.accompagnement_france_backend.demand.service.DemandService;
import org.example.accompagnement_france_backend.demand.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.UID;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/demands")
@Slf4j
public class DemandController {
    @Autowired
    private DemandScoringService demandScoringService;
    @Autowired
    private RateLimitService rateLimitService;
    @Autowired
    private DemandService demandService;


    @PostMapping
    public ResponseEntity<?> submitDemand(@Valid @RequestBody DemandDto demandDto, HttpServletRequest servletRequest) {
        String ip = servletRequest.getRemoteAddr();
        // 1. Rate Limit Check
        if (rateLimitService.isRateLimited(ip)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body((Map.of("message", "Limite de requêtes dépassée. Veuillez réessayer ultérieurement.")));
        }
        // 2. Logging BEFORE processing (No PII)
        log.info("Incoming demand request from IP: {} for service: {}", ip, demandDto.getWantedServiceLabel());

        // 3. Scoring Check
        if (demandScoringService.isSuspicious(demandDto)) {
            log.warn("Suspicious demand detected from IP: {}", ip);
            return ResponseEntity.ok(Map.of("message", "Votre demande a été soumise pour examen."));
        }
        demandService.createDemand(demandDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Votre demande a bien été envoyée"));
    }
    @GetMapping
    public List<DemandDto> getDemands(){
        return demandService.getAllDemands();
    }

    @GetMapping("/statics")
    public DemandStatics getDEmandStatics(){
        return demandService.prepareDemandStatics();
    }
    @PutMapping("{demandId}")
    public DemandDto changeDemandStatus(@PathVariable UUID demandId,@RequestBody DemandStatusDto demandStatus){
        return demandService.changeDemandStatus(demandId,demandStatus.getDemandStatus());
    }

}
