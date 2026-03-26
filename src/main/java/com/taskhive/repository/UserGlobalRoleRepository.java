package com.taskhive.repository;

import com.taskhive.model.User;
import com.taskhive.model.UserGlobalRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGlobalRoleRepository extends JpaRepository<UserGlobalRole, Long> {
    List<UserGlobalRole> findByUserAndValidToIsNull(User user);
}
