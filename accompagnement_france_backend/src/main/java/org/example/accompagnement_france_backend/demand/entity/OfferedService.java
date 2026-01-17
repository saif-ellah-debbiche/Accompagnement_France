package org.example.accompagnement_france_backend.demand.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OfferedService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String label;

    private String description;

    @Column(nullable = false)
    private boolean active = true;

    private String icon;

    private String iconUrl;

}
