package com.taskhive.config;

import com.taskhive.model.TaskStatus;
import com.taskhive.repository.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TaskStatusRepository taskStatusRepository;

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
    }

    private void createStatus(String name) {
        var status = new TaskStatus();
        status.setName(name);
        taskStatusRepository.save(status);
    }
}
