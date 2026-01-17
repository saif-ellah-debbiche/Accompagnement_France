package org.example.accompagnement_france_backend.bootstrap;

import org.example.accompagnement_france_backend.user.entity.User;
import org.example.accompagnement_france_backend.user.model.Role;
import org.example.accompagnement_france_backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin_default_email}")
    private String defaultEmail;
    @Value("${admin_default_password}")
    private String defaultPassword;

    public AdminBootstrap(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail(defaultEmail);
            admin.setPassword(passwordEncoder.encode(defaultPassword));
            admin.setRole(Role.ADMIN);
            admin.setMustChangePassword(true);
            admin.setEnable(true);
            admin.setCreatedAt(new Date());
            userRepository.save(admin);
        }
    }
}
