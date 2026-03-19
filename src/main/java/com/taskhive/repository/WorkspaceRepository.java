package com.taskhive.repository;

import com.taskhive.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
    List<Workspace> findByOwnerEmail(String email);
    Optional<Workspace> getWorkspaceByWorkspaceId(UUID workspaceId);
}
