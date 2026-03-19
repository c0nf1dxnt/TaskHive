package com.taskhive.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}