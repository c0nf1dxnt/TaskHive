package com.taskhive.controller;

import com.taskhive.dto.ProjectDto;
import com.taskhive.service.ProjectService;
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
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/workspaces/{workspaceId}")
    public String show(@PathVariable Long workspaceId, Model model) {
        var projects = projectService.getProjectsByWorkspace(workspaceId);
        model.addAttribute("projects", projects);
        model.addAttribute("workspaceId", workspaceId);
        return "workspace-view";
    }

    @GetMapping("/workspaces/{workspaceId}/projects/create")
    public String showCreateForm(@PathVariable Long workspaceId, Model model) {
        model.addAttribute("projectDto", new ProjectDto());
        model.addAttribute("workspaceId", workspaceId);
        return "project-create";
    }

    @PostMapping("/workspaces/{workspaceId}/projects/create")
    public String create(@PathVariable Long workspaceId, @Valid @ModelAttribute ProjectDto dto, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("workspaceId", workspaceId);
            return "project-create";
        }

        projectService.create(dto, workspaceId, principal.getName());
        return "redirect:/workspaces/" + workspaceId;
    }
}
