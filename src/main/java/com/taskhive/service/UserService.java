package com.taskhive.service;

import com.taskhive.model.User;
import com.taskhive.repository.RoleRepository;
import com.taskhive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("User with this username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with this email already exists");
        }

        var user = new User();
        user.setUsername(username);
        user.setEmail(email);

        user.setPassword(passwordEncoder.encode(rawPassword));

        var userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("System ERROR: Role ROLE_USER not found"));

        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        return userRepository.save(user);
    }
}
