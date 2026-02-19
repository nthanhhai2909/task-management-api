package io.github.nthanhhai2909.taskmanagement.internal.application.task.command;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;

import java.util.List;

public interface TaskRepository {
    void save(List<Task> tasks);
}
