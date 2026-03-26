package com.taskhive.config;

import com.taskhive.model.GlobalRole;
import com.taskhive.model.User;
import com.taskhive.model.UserGlobalRole;
import com.taskhive.repository.GlobalRoleRepository;
import com.taskhive.repository.UserGlobalRoleRepository;
import com.taskhive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final GlobalRoleRepository globalRoleRepository;
    private final UserRepository userRepository;
    private final UserGlobalRoleRepository userGlobalRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (globalRoleRepository.count() == 0) {
            createGlobalRole("ADMIN");
            createGlobalRole("USER");
        }

        if (userRepository.findByEmail("admin@taskhive.com").isEmpty()) {
            var admin = User.builder()
                    .email("admin@taskhive.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .isActive(true)
                    .build();
            admin = userRepository.save(admin);

            var adminRole = globalRoleRepository.findByRoleName("ADMIN")
                    .orElseThrow();
            var userRole = globalRoleRepository.findByRoleName("USER")
                    .orElseThrow();

            userGlobalRoleRepository.save(UserGlobalRole.builder()
                    .user(admin).globalRole(adminRole).build());
            userGlobalRoleRepository.save(UserGlobalRole.builder()
                    .user(admin).globalRole(userRole).build());
        }
    }

    private void createGlobalRole(String roleName) {
        var role = new GlobalRole();
        role.setRoleName(roleName);
        globalRoleRepository.save(role);
    }
}
