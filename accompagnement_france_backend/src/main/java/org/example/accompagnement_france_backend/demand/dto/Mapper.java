package org.example.accompagnement_france_backend.demand.dto;

import org.example.accompagnement_france_backend.demand.entity.Demand;
import org.example.accompagnement_france_backend.demand.entity.OfferedService;
import org.example.accompagnement_france_backend.demand.enums.DemandStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static Demand getDemandFromDemandDto(DemandDto demandDto, OfferedService offeredService){
        return  Demand.builder()
                .phone(demandDto.getPhoneNumber())
                .email(demandDto.getEmail())
                .source(demandDto.getSource())
                .status(DemandStatus.NEW)
                .message(demandDto.getMessage())
                .createdAt(Instant.now())
                .firstName(demandDto.getFirstName())
                .lastName(demandDto.getLastName())
                .service(offeredService)
                .build();
    }
    public static DemandDto getDemandDtoFromDemand(Demand demand){
        return  DemandDto.builder()
                .id(demand.getId())
                .phoneNumber(demand.getPhone())
                .email(demand.getEmail())
                .source(demand.getSource())
                .status(demand.getStatus())
                .message(demand.getMessage())
                .createdAt(demand.getCreatedAt())
                .firstName(demand.getFirstName())
                .lastName(demand.getLastName())
                .wantedServiceLabel(demand.getService().getLabel())
                .build();
    }

    public static List<DemandDto> getDemandsDtoFromDemands(List<Demand> demands){
        List<DemandDto> demandDtos= new ArrayList<>();
        for(Demand demand : demands){
            demandDtos.add(getDemandDtoFromDemand(demand));
        }
        return demandDtos;
    }
}
