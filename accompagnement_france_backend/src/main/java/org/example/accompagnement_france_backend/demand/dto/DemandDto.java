package org.example.accompagnement_france_backend.demand.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.accompagnement_france_backend.demand.enums.DemandSource;
import org.example.accompagnement_france_backend.demand.enums.DemandStatus;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemandDto {
    private UUID id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Le nom de famille est obligatoire")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "Le numéro de contact est obligatoire")
    @Pattern(regexp = "^\\+?[0-9\\s-]{7,20}$", message = "Format de numéro de téléphone invalide")
    private String phoneNumber;

    @NotBlank(message = "L’adresse e-mail est obligatoire.")
    @Email(message = "L’adresse e-mail n’est pas valide.")
    private String email;

    private DemandSource source;

    @NotBlank(message = "Le service est obligatoire")
    @Size(max = 100)
    private String wantedServiceLabel;

    @Size(max = 1000)
    private String message;

    private DemandStatus status;
    private Instant createdAt;

    // Honeypot field (should remain empty)
    private String website;
}
