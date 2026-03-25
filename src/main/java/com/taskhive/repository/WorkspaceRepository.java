package com.taskhive.repository;

import com.taskhive.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    List<Workspace> findByOwnerEmail(String email);
    Optional<Workspace> getWorkspaceByWorkspaceId(Long workspaceId);
}
