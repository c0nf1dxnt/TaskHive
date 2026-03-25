package com.taskhive.controller;

import com.taskhive.dto.TaskDto;
import com.taskhive.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/workspaces/{workspaceId}/projects/{projectId}")
    public String list(@PathVariable Long workspaceId, @PathVariable Long projectId, Model model) {
        var tasks = taskService.getTasksByProjectId(projectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("projectId", projectId);
        model.addAttribute("workspaceId", workspaceId);
        return "task-view";
    }

    @GetMapping("/workspaces/{workspaceId}/projects/{projectId}/tasks/create")
    public String showCreateForm(@PathVariable Long workspaceId, @PathVariable Long projectId, Model model) {
        model.addAttribute("taskDto", new TaskDto());
        model.addAttribute("projectId", projectId);
        model.addAttribute("workspaceId", workspaceId);
        return "task-create";
    }

    @PostMapping("/workspaces/{workspaceId}/projects/{projectId}/tasks/create")
    public String create(@PathVariable Long workspaceId, @PathVariable Long projectId, @Valid @ModelAttribute TaskDto dto,  BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("projectId", projectId);
            model.addAttribute("workspaceId", workspaceId);
            return "task-create";
        }

        taskService.create(dto, projectId, principal.getName());
        return "redirect:/workspaces/" + workspaceId + "/projects/" + projectId;
    }
}
