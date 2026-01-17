package org.example.accompagnement_france_backend.demand.dto;

import lombok.Data;

@Data
public class ServiceCount {
    private String serviceLabel;
    private Long demandCount;
    public ServiceCount(String label, long count) {
        this.serviceLabel = label;
        this.demandCount = count;
    }

}
