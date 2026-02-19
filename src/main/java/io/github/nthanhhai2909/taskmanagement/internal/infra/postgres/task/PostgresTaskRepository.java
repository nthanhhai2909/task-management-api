package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.TaskRepository;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgresTaskRepository implements TaskRepository {
    @Override
    public void save(List<Task> tasks) {
        // TODO implement save tasks to postgres
    }
}
