package com.taskhive.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_state_transitions")
@Getter
@Setter
public class TaskStateTransition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transition_id")
    private Long transitionId;

    @ManyToOne
    @JoinColumn(name = "from_status_id", nullable = false, updatable = false)
    private TaskStatus fromStatus;

    @ManyToOne
    @JoinColumn(name = "to_status_id", nullable = false, updatable = false)
    private TaskStatus toStatus;
}
