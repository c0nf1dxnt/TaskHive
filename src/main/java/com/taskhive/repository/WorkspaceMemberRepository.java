package com.taskhive.repository;

import com.taskhive.model.User;
import com.taskhive.model.Workspace;
import com.taskhive.model.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {

    Optional<WorkspaceMember> findByWorkspaceAndUserAndValidToIsNull(Workspace workspace, User user);

    List<WorkspaceMember> findByWorkspaceAndValidToIsNull(Workspace workspace);

    List<WorkspaceMember> findByUserAndValidToIsNull(User user);
}
