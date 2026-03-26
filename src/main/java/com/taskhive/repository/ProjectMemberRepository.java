package com.taskhive.repository;

import com.taskhive.model.Project;
import com.taskhive.model.ProjectMember;
import com.taskhive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    Optional<ProjectMember> findByProjectAndUserAndValidToIsNull(Project project, User user);

    List<ProjectMember> findByProjectAndValidToIsNull(Project project);
}
