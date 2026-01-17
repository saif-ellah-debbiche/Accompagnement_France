package org.example.accompagnement_france_backend.auth.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String accessToken;
    private boolean mustChangePassword;
}
