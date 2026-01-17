package org.example.accompagnement_france_backend.demand.dto;

import lombok.Data;
import org.example.accompagnement_france_backend.demand.enums.DemandStatus;

@Data
public class DemandStatusDto {
    private DemandStatus demandStatus;

    public DemandStatus getDemandStatus() {
        return demandStatus;
    }

    public void setDemandStatus(DemandStatus demandStatus) {
        this.demandStatus = demandStatus;
    }
}
