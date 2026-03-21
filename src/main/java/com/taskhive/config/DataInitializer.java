package com.taskhive.config;

import com.taskhive.model.TaskStateTransition;
import com.taskhive.model.TaskStatus;
import com.taskhive.repository.TaskStateTransitionRepository;
import com.taskhive.repository.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TaskStatusRepository taskStatusRepository;

    private final TaskStateTransitionRepository taskStateTransitionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (taskStatusRepository.count() == 0) {
            createStatus("BACKLOG");
            createStatus("TODO");
            createStatus("IN_PROGRESS");
            createStatus("IN_REVIEW");
            createStatus("DONE");
            createStatus("CANCELLED");
        }

        if (taskStateTransitionRepository.count() == 0) {
            createStateTransition("BACKLOG", "TODO");
            createStateTransition("TODO", "IN_PROGRESS");
            createStateTransition("IN_PROGRESS", "IN_REVIEW");
            createStateTransition("IN_PROGRESS", "TODO");
            createStateTransition("IN_REVIEW", "DONE");
            createStateTransition("IN_REVIEW", "IN_PROGRESS");
            createStateTransition("IN_REVIEW", "CANCELLED");
            createStateTransition("TODO", "CANCELLED");
            createStateTransition("IN_PROGRESS", "CANCELLED");
            createStateTransition("BACKLOG", "CANCELLED");
        }
    }

    private void createStateTransition(String from, String to) {
        var transition = new TaskStateTransition();
        var fromStatus = taskStatusRepository.findByName(from);
        var toStatus = taskStatusRepository.findByName(to);
        if (fromStatus.isPresent() && toStatus.isPresent()) {
            transition.setFromStatus(fromStatus.get());
            transition.setToStatus(toStatus.get());
            taskStateTransitionRepository.save(transition);
        } else {
            throw new RuntimeException("Incorrect task status");
        }
    }

    private void createStatus(String name) {
        var status = new TaskStatus();
        status.setName(name);
        taskStatusRepository.save(status);
    }
}
