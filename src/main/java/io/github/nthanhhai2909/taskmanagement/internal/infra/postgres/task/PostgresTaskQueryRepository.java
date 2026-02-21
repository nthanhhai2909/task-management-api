package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.TaskQueryRepository;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.ListTasksHandler;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class PostgresTaskQueryRepository implements TaskQueryRepository {
    private final PostgresTaskFindRepository postgresTaskFindRepository;
    private final PostgresTaskFindByTaskID postgresTaskFindByID;
    public PostgresTaskQueryRepository(JdbcTemplate jdbcTemplate, TaskRecordMapper mapper) {
        this.postgresTaskFindRepository = new PostgresTaskFindRepository(jdbcTemplate, mapper);
        this.postgresTaskFindByID = new PostgresTaskFindByTaskID(jdbcTemplate, mapper);
    }
    @Override
    public List<Task> list(ListTasksHandler.ListTasksQuery query, int offset, int limit, String sort) {
        return this.postgresTaskFindRepository.list(query, offset, limit, sort);
    }

    @Override
    public long count(ListTasksHandler.ListTasksQuery query) {
        return this.postgresTaskFindRepository.count(query);
    }

    @Override
    public Optional<Task> findById(TaskID id) {
        return this.postgresTaskFindByID.findById(id);
    }

}
