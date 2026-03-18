package com.taskhive.service;

import com.taskhive.dto.UserLoginDto;
import com.taskhive.dto.UserRegistrationDto;
import com.taskhive.model.User;
import com.taskhive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(UserRegistrationDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        var user = User.builder()
                .email(dto.getEmail())
                .passwordHash(dto.getPassword())
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

    public User login(UserLoginDto dto) {
        var user = userRepository.findByEmail(dto.getEmail());

        if (user.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        if (!user.get().getPasswordHash().equals(dto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return user.get();
    }
}
