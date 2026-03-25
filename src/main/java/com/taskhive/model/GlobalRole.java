package com.taskhive.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "global_roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_role_seq")
    @SequenceGenerator(name = "global_role_seq", sequenceName = "global_roles_seq", allocationSize = 50)
    @Column(name = "global_role_id")
    private Long globalRoleId;

    @Column(nullable = false, unique = true, name = "role_name")
    private String roleName;
}
