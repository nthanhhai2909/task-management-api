package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class PostgresTaskFindByTaskID {
    private final JdbcTemplate jdbcTemplate;
    private final TaskRecordMapper mapper;

    private static final String SELECT_BY_ID_SQL =
            "SELECT " +
                    "id, sid, title, description, created_by, created_at, " +
                    "assignee, priority, status, due_date, version, deleted " +
                    "FROM tasks " +
                    "WHERE id = ?";

    private static final String SELECT_BY_SID_SQL =
            "SELECT " +
                    "id, sid, title, description, created_by, created_at, " +
                    "assignee, priority, status, due_date, version, deleted " +
                    "FROM tasks " +
                    "WHERE sid = ?";

    public PostgresTaskFindByTaskID(JdbcTemplate jdbcTemplate, TaskRecordMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public Optional<Task> findById(TaskID id) {
        if (id == null || id.isEmpty()) {
            return Optional.empty();
        }

        try {
            List<TaskRecord> results;
            if (id.lid() != null) {
                results = jdbcTemplate.query(SELECT_BY_ID_SQL, mapper, id.lid());
            } else {
                results = jdbcTemplate.query(SELECT_BY_SID_SQL, mapper, id.sid());
            }
            if (results.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(mapper.toDomain(results.get(0)));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
