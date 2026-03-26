package com.taskhive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Project key is required")
    @Size(min = 2, max = 10, message = "Key must be 2-10 characters")
    @Pattern(regexp = "^[A-Z][A-Z0-9]*$", message = "Key must be uppercase letters/numbers, starting with a letter")
    private String projectKey;

    private String description;
}
