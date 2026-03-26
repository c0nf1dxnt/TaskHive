package com.taskhive.repository;

import com.taskhive.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByWorkspaceWorkspaceId(Long workspaceId);

    boolean existsByWorkspaceWorkspaceIdAndProjectKey(Long workspaceId, String projectKey);
}
