package org.example.accompagnement_france_backend.demand.service;

import org.example.accompagnement_france_backend.demand.dto.DemandDto;
import org.example.accompagnement_france_backend.demand.dto.DemandStatics;
import org.example.accompagnement_france_backend.demand.dto.Mapper;
import org.example.accompagnement_france_backend.demand.entity.Demand;
import org.example.accompagnement_france_backend.demand.entity.OfferedService;
import org.example.accompagnement_france_backend.demand.enums.DemandStatus;
import org.example.accompagnement_france_backend.demand.repository.DemandRepository;
import org.example.accompagnement_france_backend.demand.repository.OfferedServiceRepository;
import org.example.accompagnement_france_backend.email.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DemandService {
    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private OfferedServiceRepository offeredServiceRepository;
    @Autowired
    private EmailNotificationService emailNotificationService;


    public Demand createDemand(DemandDto demandDto){
        OfferedService wantedService = offeredServiceRepository.findServiceByLabel(demandDto.getWantedServiceLabel()).orElseThrow(()->new IllegalArgumentException("Le service sélectionné n'existe pas ou n'est pas disponible."));
        Demand newDemand = Mapper.getDemandFromDemandDto(demandDto,wantedService);
        Demand savedDemand =  demandRepository.save(newDemand);
        emailNotificationService.notifyAdminNewDemand(demandDto);
        return savedDemand;
    }




    public List<DemandDto> getAllDemands(){
        return Mapper.getDemandsDtoFromDemands(demandRepository.findAll());
    }
    private Demand findDemand(UUID demandId){
        return demandRepository.findById(demandId).orElseThrow(()->new ResponseStatusException(   HttpStatus.NOT_FOUND,
                "Demande introuvable"));
    }
    public DemandDto changeDemandStatus(UUID demandId ,DemandStatus demandStatus){
        Demand demand = findDemand(demandId);
        demand.setStatus(demandStatus);
        if(demandStatus==DemandStatus.CLOSED){
            demand.setClosedAt(Instant.now());
        }
        return Mapper.getDemandDtoFromDemand(demandRepository.save(demand));

    }

    public DemandStatics prepareDemandStatics() {
        Pageable popularServicesNumber = PageRequest.of(0, 3);
        Instant thirtyDaysAgo = Instant.now().minus(30, ChronoUnit.DAYS);
        Instant sixtyDaysAgo = Instant.now().minus(60, ChronoUnit.DAYS);

        long lastMonthCount = demandRepository.countByCreatedAtBetween(sixtyDaysAgo, thirtyDaysAgo);
        long thisMonthCount = demandRepository.countByCreatedAtAfter(thirtyDaysAgo);
        int percentageChangeLast30Days=0;

        if (lastMonthCount == 0) {
            percentageChangeLast30Days = thisMonthCount > 0 ? 100 : 0; // avoid divide by zero
        }else{
            double change = ((double) (thisMonthCount - lastMonthCount) / lastMonthCount) * 100;

            // Round to int (clean, no comma)
            percentageChangeLast30Days = (int) Math.round(change);
        }

        return DemandStatics.builder()
                .fromDays(30)
                .percentageChangeLastDays(percentageChangeLast30Days)
                .popularServices(demandRepository.findMostPopularServiceLabels(popularServicesNumber))
                .closedDemands(demandRepository.countByStatusAndCreatedAtAfter(DemandStatus.CLOSED,thirtyDaysAgo))
                .demandsInProgressing(demandRepository.countAllByStatus(DemandStatus.IN_PROGRESS))
                .demandsInWaitingStatus(demandRepository.countAllByStatus(DemandStatus.NEW))
                .totalDemands(thisMonthCount)
                .recentActivities(Mapper.getDemandsDtoFromDemands(demandRepository.findAllByStatus(DemandStatus.NEW)))
                .build();
    }
}

