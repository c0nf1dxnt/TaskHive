package com.taskhive.repository;

import com.taskhive.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByWorkspaceWorkspaceId(UUID workspaceId);
}
