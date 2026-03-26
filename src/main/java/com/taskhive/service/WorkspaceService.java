package com.taskhive.service;

import com.taskhive.dto.WorkspaceDto;
import com.taskhive.model.*;
import com.taskhive.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    public Workspace getById(Long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));
    }

    public void checkMembership(Long workspaceId, String email) {
        var workspace = getById(workspaceId);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        workspaceMemberRepository.findByWorkspaceAndUserAndValidToIsNull(workspace, user)
                .orElseThrow(() -> new RuntimeException("Access denied: not a workspace member"));
    }

    public WorkspaceMember getMembership(Long workspaceId, String email) {
        var workspace = getById(workspaceId);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return workspaceMemberRepository.findByWorkspaceAndUserAndValidToIsNull(workspace, user)
                .orElse(null);
    }

    public List<Workspace> getWorkspacesByEmail(String email) {
        return workspaceRepository.findByActiveMemberEmail(email);
    }

    public List<WorkspaceMember> getActiveMembers(Long workspaceId) {
        var workspace = getById(workspaceId);
        return workspaceMemberRepository.findByWorkspaceAndValidToIsNull(workspace);
    }

    @Transactional
    public void addMember(Long workspaceId, String email) {
        var workspace = getById(workspaceId);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var existing = workspaceMemberRepository
                .findByWorkspaceAndUserAndValidToIsNull(workspace, user);

        if (existing.isPresent()) {
            throw new RuntimeException("User is already a member");
        }

        var member = WorkspaceMember.builder()
                .workspace(workspace)
                .user(user)
                .role(WorkspaceRole.MEMBER)
                .status(WorkspaceMemberStatus.ACTIVE)
                .build();

        workspaceMemberRepository.save(member);
    }

    @Transactional
    public void removeMember(Long memberId) {
        var member = workspaceMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setStatus(WorkspaceMemberStatus.LEFT);
        member.setValidTo(Instant.now());
        workspaceMemberRepository.save(member);
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
