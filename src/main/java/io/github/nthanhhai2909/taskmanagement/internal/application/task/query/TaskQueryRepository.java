package io.github.nthanhhai2909.taskmanagement.internal.application.task.query;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;

import java.util.List;
import java.util.Optional;

public interface TaskQueryRepository {
    List<Task> list(ListTasksHandler.ListTasksQuery query, int offset, int limit, String sort);

    long count(ListTasksHandler.ListTasksQuery query);

    Optional<Task> findById(TaskID id);
}
