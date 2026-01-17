package org.example.accompagnement_france_backend.config.security;


import lombok.RequiredArgsConstructor;
import org.example.accompagnement_france_backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.ORIGIN;
import static org.springframework.http.HttpMethod.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${cors.allowed-origins}")
    private String allowedOriginsConfig;

    private final SpecialLogoutHandler logoutHandler;
    private final UserRepository userRepository;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http
            ,JWTAuthenticationFilter jwtAuthFilter,
             PolicyEnforcementFilter adminAccessFilter,
             CustomAuthenticationEntryPoint authenticationEntryPoint){
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/auth/change-password", "/auth/logout").authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/demands").permitAll()
                        .requestMatchers("/demands/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/services").permitAll()
                        .requestMatchers("/services/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout((logout) ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessUrl("/login")
                                .logoutSuccessHandler(((request, response, authentication) ->
                                 SecurityContextHolder.clearContext()))

                ).exceptionHandling(handling -> handling
                        .authenticationEntryPoint(authenticationEntryPoint) // Use the bean
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");
                            try {
                                response.getWriter().write("{\"message\": \"Access Denied\"}");
                            } catch (IOException e) {
                                System.err.println("Failed to write access denied response: " + e.getMessage());
                            }
                        })
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(adminAccessFilter, JWTAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        List<String> allowedOrigins = Arrays.asList(allowedOriginsConfig.split("\\s*,\\s*"));
        config.setAllowCredentials(true);
        // Include both localhost:4200 and your GitHub Pages domain for production
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedHeaders(Arrays.asList(ORIGIN, CONTENT_TYPE, ACCEPT, AUTHORIZATION));
        config.setAllowedMethods(Arrays.asList(GET.name(), POST.name(), DELETE.name(), PUT.name(), PATCH.name(), OPTIONS.name()));

        source.registerCorsConfiguration("/**", config);
        return source;
    }
//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//        config.setAllowedHeaders(Arrays.asList(
//                ORIGIN,
//                CONTENT_TYPE,
//                ACCEPT,
//                AUTHORIZATION
//        ));
//        config.setAllowedMethods(Arrays.asList(
//                GET.name(),
//                POST.name(),
//                DELETE.name(),
//                PUT.name(),
//                PATCH.name(),
//                OPTIONS.name()
//        ));
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}
