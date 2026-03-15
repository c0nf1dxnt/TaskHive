package com.taskhive.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "workspace_members")
@Getter
@Setter
public class WorkspaceMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "workspace_member_id")
    private UUID workspaceMemberId;

    @ManyToOne
    @JoinColumn(name = "workspace_id", nullable = false, updatable = false)
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "workspace_role", nullable = false)
    private WorkspaceRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "workspace_member_status", nullable = false)
    private WorkspaceMemberStatus status;

    @Column(name = "valid_from", nullable = false, updatable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;

    @PrePersist
    protected void onCreate() {
        validFrom = LocalDateTime.now();
    }
}
