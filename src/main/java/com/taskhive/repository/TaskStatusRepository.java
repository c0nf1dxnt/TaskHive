package com.taskhive.repository;

import com.taskhive.model.TaskStatus;
import com.taskhive.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    Optional<TaskStatus> findByNameAndWorkspace(String name, Workspace workspace);

    List<TaskStatus> findByWorkspaceOrderBySortOrder(Workspace workspace);

    long countByWorkspace(Workspace workspace);
}
