package com.taskhive.controller;

import com.taskhive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String dashboard(Model model) {
        var users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/dashboard";
    }

    @GetMapping("/users/{userId}")
    public String userDetail(@PathVariable Long userId, Model model) {
        var user = userService.getById(userId);
        var roles = userService.getActiveRoles(user);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("isAdmin", userService.hasRole(user, "ADMIN"));
        return "admin/user-detail";
    }

    @PostMapping("/users/{userId}/deactivate")
    public String deactivate(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        userService.deactivate(userId);
        redirectAttributes.addFlashAttribute("success", "User deactivated");
        return "redirect:/admin/users/" + userId;
    }

    @PostMapping("/users/{userId}/activate")
    public String activate(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        userService.activate(userId);
        redirectAttributes.addFlashAttribute("success", "User activated");
        return "redirect:/admin/users/" + userId;
    }

    @PostMapping("/users/{userId}/grant-admin")
    public String grantAdmin(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        var user = userService.getById(userId);
        userService.assignRole(user, "ADMIN");
        redirectAttributes.addFlashAttribute("success", "Admin role granted");
        return "redirect:/admin/users/" + userId;
    }

    @PostMapping("/users/{userId}/revoke-admin")
    public String revokeAdmin(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        userService.revokeRole(userId, "ADMIN");
        redirectAttributes.addFlashAttribute("success", "Admin role revoked");
        return "redirect:/admin/users/" + userId;
    }
}
