package com.taskhive.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_statuses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"workspace_id", "status_name"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_status_seq")
    @SequenceGenerator(name = "task_status_seq", sequenceName = "task_statuses_seq", allocationSize = 50)
    @Column(name = "status_id")
    private Long statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false, updatable = false)
    private Workspace workspace;

    @Column(name = "status_name", nullable = false)
    private String name;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}
