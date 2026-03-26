package com.taskhive.service;

import com.taskhive.dto.TaskDto;
import com.taskhive.dto.TaskEditDto;
import com.taskhive.model.Task;
import com.taskhive.model.TaskStatus;
import com.taskhive.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStateTransitionRepository taskStateTransitionRepository;

    @Transactional
    public Task create(TaskDto dto, Long projectId, String email) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        var creator = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var workspace = project.getWorkspace();
        var status = taskStatusRepository.findByNameAndWorkspace("BACKLOG", workspace)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        int nextNumber = taskRepository.findMaxTaskNumberByProject(projectId) + 1;

        var task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .project(project)
                .creator(creator)
                .status(status)
                .taskNumber(nextNumber)
                .build();

        return taskRepository.save(task);
    }

    public Task getById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectProjectId(projectId);
    }

    @Transactional
    public Task update(Long taskId, TaskEditDto dto) {
        var task = getById(taskId);

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());

        if (dto.getAssigneeEmail() != null && !dto.getAssigneeEmail().isBlank()) {
            var assignee = userRepository.findByEmail(dto.getAssigneeEmail())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignee(assignee);
        } else {
            task.setAssignee(null);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public void changeStatus(Long taskId, Long newStatusId) {
        var task = getById(taskId);

        var newStatus = taskStatusRepository.findById(newStatusId)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        var isAllowed = taskStateTransitionRepository
                .existsByFromStatusAndToStatus(task.getStatus(), newStatus);

        if (!isAllowed) {
            throw new RuntimeException("Status transition not allowed");
        }

        task.setStatus(newStatus);
        taskRepository.save(task);
    }

    @Transactional
    public void softDelete(Long taskId) {
        var task = getById(taskId);
        task.setDeletedAt(Instant.now());
        taskRepository.save(task);
    }

    public List<TaskStatus> getAllowedTransitions(Task task) {
        return taskStateTransitionRepository.findByFromStatus(task.getStatus())
                .stream()
                .map(t -> t.getToStatus())
                .toList();
    }
}
