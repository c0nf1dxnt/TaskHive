package com.taskhive.repository;

import com.taskhive.model.TaskStateTransition;
import com.taskhive.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskStateTransitionRepository extends JpaRepository<TaskStateTransition, Long> {
    boolean existsByFromStatusAndToStatus(TaskStatus from, TaskStatus to);

    List<TaskStateTransition> findByFromStatus(TaskStatus fromStatus);
}
