package org.example.accompagnement_france_backend.email.brevo;

import brevo.ApiClient;
import brevo.auth.ApiKeyAuth;
import brevoApi.AccountApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BrevoConfig {
    @Value("${brevo.api.key}")
    private String apiKey;

    @Bean
    public ApiClient brevoApiClient() {
        ApiClient client = brevo.Configuration.getDefaultApiClient();

        ApiKeyAuth apiKeyAuth =
                (ApiKeyAuth) client.getAuthentication("api-key");
        apiKeyAuth.setApiKey(apiKey);
        return client;
    }

    @Bean
    public AccountApi accountApi(ApiClient brevoApiClient) {
        return new AccountApi(brevoApiClient);
    }
}
