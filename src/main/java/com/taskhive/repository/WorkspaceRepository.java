package com.taskhive.repository;

import com.taskhive.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    @Query("SELECT w FROM Workspace w JOIN WorkspaceMember wm ON wm.workspace = w " +
           "WHERE wm.user.email = :email AND wm.validTo IS NULL")
    List<Workspace> findByActiveMemberEmail(String email);
}
