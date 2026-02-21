package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.TaskCommandRepository;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class PostgresTaskCommandRepository implements TaskCommandRepository {
    private final PostgresTaskFindByTaskID postgresTaskFindByTaskID;
    private final PostgresTaskSaveRepository postgresTaskSaveRepository;

    public PostgresTaskCommandRepository(JdbcTemplate jdbcTemplate, TaskRecordMapper mapper) {
        this.postgresTaskFindByTaskID = new PostgresTaskFindByTaskID(jdbcTemplate, mapper);
        this.postgresTaskSaveRepository = new PostgresTaskSaveRepository(jdbcTemplate, mapper);
    }

    @Override
    public void save(List<Task> tasks) {
        this.postgresTaskSaveRepository.save(tasks);
    }

    @Override
    public Optional<Task> findById(TaskID id) {
        return this.postgresTaskFindByTaskID.findById(id);
    }
}
