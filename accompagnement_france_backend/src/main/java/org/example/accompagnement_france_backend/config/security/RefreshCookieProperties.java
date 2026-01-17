package org.example.accompagnement_france_backend.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.security.cookie")
@Getter
@Setter
public class RefreshCookieProperties {
    private boolean secure;
    private String sameSite;
    private String path;
}
