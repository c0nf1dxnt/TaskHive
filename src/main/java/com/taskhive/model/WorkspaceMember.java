package com.taskhive.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "workspace_members")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workspace_member_seq")
    @SequenceGenerator(name = "workspace_member_seq", sequenceName = "workspace_members_seq", allocationSize = 50)
    @Column(name = "workspace_member_id")
    private Long workspaceMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false, updatable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "workspace_role", nullable = false)
    private WorkspaceRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "workspace_member_status", nullable = false)
    private WorkspaceMemberStatus status;

    @Column(name = "valid_from", nullable = false, updatable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @PrePersist
    protected void onCreate() {
        validFrom = Instant.now();
    }
}
