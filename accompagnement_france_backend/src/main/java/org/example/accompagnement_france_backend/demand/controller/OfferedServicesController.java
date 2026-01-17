package org.example.accompagnement_france_backend.demand.controller;

import org.example.accompagnement_france_backend.demand.entity.OfferedService;
import org.example.accompagnement_france_backend.demand.repository.OfferedServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services")
public class OfferedServicesController {
    @Autowired private OfferedServiceRepository offeredServiceRepository;
    @GetMapping
    public List<OfferedService> getServices(){
        return offeredServiceRepository.findAll();
    }
}
