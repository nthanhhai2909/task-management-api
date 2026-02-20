package io.github.nthanhhai2909.taskmanagement.internal.application.task.command;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void save(List<Task> tasks);

    Optional<Task> findById(TaskID id);
}
