package com.taskhive.repository;

import com.taskhive.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectProjectId(Long projectId);

    @Query("SELECT COALESCE(MAX(t.taskNumber), 0) FROM Task t WHERE t.project.projectId = :projectId")
    int findMaxTaskNumberByProject(Long projectId);

    Optional<Task> findByProjectProjectIdAndTaskNumber(Long projectId, Integer taskNumber);
}
