package com.taskhive.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceDto {
    @NotBlank(message = "Workspace name is required")
    private String name;
}