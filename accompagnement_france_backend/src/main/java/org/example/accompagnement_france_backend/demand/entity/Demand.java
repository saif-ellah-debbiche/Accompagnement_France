package org.example.accompagnement_france_backend.demand.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.accompagnement_france_backend.demand.enums.DemandSource;
import org.example.accompagnement_france_backend.demand.enums.DemandStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "demands")
public class Demand {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String message;

    @Enumerated(EnumType.STRING)
    private DemandSource source;

    @Enumerated(EnumType.STRING)
    private DemandStatus status;

    @ManyToOne
    private OfferedService service;

    private Instant createdAt;
    private Instant closedAt;
}
