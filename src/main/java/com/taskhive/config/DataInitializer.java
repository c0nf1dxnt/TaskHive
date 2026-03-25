package com.taskhive.config;

import com.taskhive.model.GlobalRole;
import com.taskhive.repository.GlobalRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final GlobalRoleRepository globalRoleRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (globalRoleRepository.count() == 0) {
            createGlobalRole("ADMIN");
            createGlobalRole("USER");
        }
    }

    private void createGlobalRole(String roleName) {
        var role = new GlobalRole();
        role.setRoleName(roleName);
        globalRoleRepository.save(role);
    }
}
