package com.taskhive.service;

import com.taskhive.dto.WorkspaceDto;
import com.taskhive.model.Workspace;
import com.taskhive.repository.UserRepository;
import com.taskhive.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final UserRepository userRepository;

    public Workspace create(WorkspaceDto dto, String email) {
        var owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var workspace = Workspace.builder()
                .name(dto.getName())
                .owner(owner)
                .build();

        return workspaceRepository.save(workspace);
    }

    public List<Workspace> getWorkspacesByEmail(String email) {
        return workspaceRepository.findByOwnerEmail(email);
    }
}
