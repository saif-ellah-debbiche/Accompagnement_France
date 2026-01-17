package org.example.accompagnement_france_backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    private String value;
}
