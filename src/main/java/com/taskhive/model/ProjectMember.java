package com.taskhive.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "project_members")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_member_seq")
    @SequenceGenerator(name = "project_member_seq", sequenceName = "project_members_seq", allocationSize = 50)
    @Column(name = "project_member_id")
    private Long projectMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_role", nullable = false)
    private ProjectRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_member_status", nullable = false)
    private ProjectMemberStatus status;

    @Column(name = "valid_from", nullable = false, updatable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @PrePersist
    protected void onCreate() {
        validFrom = Instant.now();
    }
}
