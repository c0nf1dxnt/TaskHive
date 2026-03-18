package com.taskhive.controller;

import com.taskhive.dto.WorkspaceDto;
import com.taskhive.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/workspaces")
    public String list(Model model, Principal principal) {
        var workspaces = workspaceService.getWorkspacesByEmail(principal.getName());
        model.addAttribute("workspaces", workspaces);
        return "workspaces";
    }

    @GetMapping("/workspaces/create")
    public String showCreateForm(Model model) {
        model.addAttribute("workspaceDto", new WorkspaceDto());
        return "workspace-create";
    }

    @PostMapping("/workspaces/create")
    public String create(@Valid @ModelAttribute WorkspaceDto dto, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "workspace-create";
        }

        workspaceService.create(dto, principal.getName());
        return "redirect:/workspaces";
    }
}
