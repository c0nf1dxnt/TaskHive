package com.taskhive.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user_global_roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGlobalRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_global_role_seq")
    @SequenceGenerator(name = "user_global_role_seq", sequenceName = "user_global_roles_seq", allocationSize = 50)
    @Column(name = "user_global_role_id")
    private Long userGlobalRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "global_role_id", nullable = false, updatable = false)
    private GlobalRole globalRole;

    @Column(name = "valid_from", nullable = false, updatable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @PrePersist
    protected void onCreate() {
        validFrom = Instant.now();
    }
}
