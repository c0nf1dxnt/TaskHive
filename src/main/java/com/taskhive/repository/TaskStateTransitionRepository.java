package com.taskhive.repository;

import com.taskhive.model.TaskStateTransition;
import com.taskhive.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStateTransitionRepository extends JpaRepository<TaskStateTransition, Long> {
    boolean existsByFromStatusAndToStatus(TaskStatus from, TaskStatus to);
}
