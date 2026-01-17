package org.example.accompagnement_france_backend.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.accompagnement_france_backend.user.entity.User;
import org.example.accompagnement_france_backend.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PolicyEnforcementFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    public PolicyEnforcementFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String method = request.getMethod();
        boolean isOptionsRequest = "OPTIONS".equalsIgnoreCase(method);

        // 1. Skip logic for CORS pre-flight to avoid headers issues
        if (isOptionsRequest) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 2. Only check if user is authenticated and is our custom User object
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User user) {

            // Check 1: Is account disabled?
            if (!user.isEnable()) {
                sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, "Account is disabled. Please contact support.");
                return; // Stop chain
            }

            // Check 2: Must change password?
            boolean mustChangePassword = user.isMustChangePassword();
            boolean isChangePasswordEndpoint = isMatchingPath(request, "/auth/change-password");

            if (mustChangePassword && !isChangePasswordEndpoint) {
                sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, "You must change your password before accessing other endpoints.");
                return; // Stop chain
            }
        }

        // 3. If all checks passed (or user is anonymous), proceed
        filterChain.doFilter(request, response);
    }

    // Helper method to ensure we always send valid JSON
    private void sendJsonError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Simple JSON structure
        String jsonResponse = String.format("{\"message\": \"%s\"}", message.replace("\"", "\\\""));

        response.getWriter().write(jsonResponse);
        // No need to close() here, returning is enough for Spring to handle it
    }

    // Helper to check paths safely (avoids the .contains() bug)
    private boolean isMatchingPath(HttpServletRequest request, String pathToCheck) {
        String requestPath = request.getRequestURI();
        // This ensures we don't accidentally allow /admin/change-password-status
        // It strictly checks if the request is specifically /auth/change-password
        return requestPath.equals(pathToCheck) || requestPath.startsWith(pathToCheck + "/");
    }
}
