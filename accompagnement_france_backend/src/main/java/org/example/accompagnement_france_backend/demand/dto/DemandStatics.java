package org.example.accompagnement_france_backend.demand.dto;

import lombok.Builder;
import lombok.Data;
import org.example.accompagnement_france_backend.demand.entity.OfferedService;

import java.util.List;
@Data
@Builder
public class DemandStatics {
    private long fromDays=30;
    private long totalDemands;
    private Integer demandsInWaitingStatus;
    private Integer demandsInProgressing;
    private long closedDemands;

    private List<ServiceCount> popularServices;
    private int percentageChangeLastDays;
    private List<DemandDto> recentActivities;

}
