package org.example.accompagnement_france_backend.user.service;

import org.example.accompagnement_france_backend.user.entity.User;
import org.example.accompagnement_france_backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean existUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
