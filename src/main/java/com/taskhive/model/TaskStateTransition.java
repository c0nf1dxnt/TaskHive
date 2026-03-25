package com.taskhive.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_state_transitions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"from_status_id", "to_status_id"})
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStateTransition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_state_transition_seq")
    @SequenceGenerator(name = "task_state_transition_seq", sequenceName = "task_state_transitions_seq", allocationSize = 50)
    @Column(name = "transition_id")
    private Long transitionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_status_id", nullable = false, updatable = false)
    private TaskStatus fromStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_status_id", nullable = false, updatable = false)
    private TaskStatus toStatus;
}
