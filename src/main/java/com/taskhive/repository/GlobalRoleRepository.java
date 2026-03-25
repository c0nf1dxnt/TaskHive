package com.taskhive.repository;

import com.taskhive.model.GlobalRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GlobalRoleRepository extends JpaRepository<GlobalRole, Long> {
    Optional<GlobalRole> findByRoleName(String roleName);
}
