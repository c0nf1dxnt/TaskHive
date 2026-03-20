package com.taskhive.service;

import com.taskhive.dto.ProjectDto;
import com.taskhive.model.Project;
import com.taskhive.repository.ProjectRepository;
import com.taskhive.repository.UserRepository;
import com.taskhive.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final WorkspaceRepository workspaceRepository;

    public Project create(ProjectDto dto, UUID workspaceId, String email) {
        var creator = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        var project = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .creator(creator)
                .workspace(workspace)
                .build();

        return projectRepository.save(project);
    }

    public List<Project> getProjectsByWorkspace(UUID workspaceId) {
        return projectRepository.findByWorkspaceWorkspaceId(workspaceId);
    }
}
