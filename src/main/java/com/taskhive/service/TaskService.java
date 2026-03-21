package com.taskhive.service;

import com.taskhive.dto.TaskDto;
import com.taskhive.model.Task;
import com.taskhive.model.TaskStatus;
import com.taskhive.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final TaskStatusRepository taskStatusRepository;
    private final TaskStateTransitionRepository taskStateTransitionRepository;

    public Task create(TaskDto dto, UUID projectId, String email) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        var creator = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var status = taskStatusRepository.findByName("BACKLOG")
                .orElseThrow(() -> new RuntimeException("Status not found"));

        var task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .project(project)
                .creator(creator)
                .status(status)
                .build();

        return taskRepository.save(task);
    }

    public List<Task> getTasksByProjectId(UUID projectId) {
        return taskRepository.findByProjectProjectId(projectId);
    }

    public void changeStatus(UUID taskId, String newStatusName, String email) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        var status = taskStatusRepository.findByName(newStatusName)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        var isTransitionAllowed = taskStateTransitionRepository.existsByFromStatusAndToStatus(task.getStatus(), status);

        if (isTransitionAllowed) {
            task.setStatus(status);
            taskRepository.save(task);
        } else {
            throw new RuntimeException("Incorrect task status transition attempt");
        }
    }
}
