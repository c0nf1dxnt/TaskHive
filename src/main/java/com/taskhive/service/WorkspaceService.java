package com.taskhive.service;

import com.taskhive.dto.WorkspaceDto;
import com.taskhive.model.*;
import com.taskhive.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStateTransitionRepository taskStateTransitionRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Transactional
    public Workspace create(WorkspaceDto dto, String email) {
        var owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var workspace = Workspace.builder()
                .name(dto.getName())
                .owner(owner)
                .build();

        workspace = workspaceRepository.save(workspace);

        addOwnerAsMember(workspace, owner);
        initDefaultStatuses(workspace);

        return workspace;
    }

    public List<Workspace> getWorkspacesByEmail(String email) {
        return workspaceRepository.findByOwnerEmail(email);
    }

    private void addOwnerAsMember(Workspace workspace, User owner) {
        var member = WorkspaceMember.builder()
                .workspace(workspace)
                .user(owner)
                .role(WorkspaceRole.ADMIN)
                .status(WorkspaceMemberStatus.ACTIVE)
                .build();
        workspaceMemberRepository.save(member);
    }

    private void initDefaultStatuses(Workspace workspace) {
        var backlog = createStatus(workspace, "BACKLOG", 1);
        var todo = createStatus(workspace, "TODO", 2);
        var inProgress = createStatus(workspace, "IN_PROGRESS", 3);
        var inReview = createStatus(workspace, "IN_REVIEW", 4);
        var done = createStatus(workspace, "DONE", 5);
        var cancelled = createStatus(workspace, "CANCELLED", 6);

        createTransition(backlog, todo);
        createTransition(todo, inProgress);
        createTransition(inProgress, inReview);
        createTransition(inProgress, todo);
        createTransition(inReview, done);
        createTransition(inReview, inProgress);
        createTransition(inReview, cancelled);
        createTransition(todo, cancelled);
        createTransition(inProgress, cancelled);
        createTransition(backlog, cancelled);
    }

    private TaskStatus createStatus(Workspace workspace, String name, int sortOrder) {
        var status = TaskStatus.builder()
                .workspace(workspace)
                .name(name)
                .sortOrder(sortOrder)
                .build();
        return taskStatusRepository.save(status);
    }

    private void createTransition(TaskStatus from, TaskStatus to) {
        var transition = TaskStateTransition.builder()
                .fromStatus(from)
                .toStatus(to)
                .build();
        taskStateTransitionRepository.save(transition);
    }
}
