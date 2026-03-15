package com.taskhive.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "global_roles")
@Getter
@Setter
public class GlobalRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "global_role_id")
    private Long globalRoleId;

    @Column(nullable = false, unique = true, name = "role_name")
    private String roleName;
}
