package com.taskhive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMemberDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
}
