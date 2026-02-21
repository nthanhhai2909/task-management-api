package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostgresTaskSaveRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TaskRecordMapper mapper;
    private static final int BATCH_SIZE = 500;

    private static final String UPSERT_SQL =
            "INSERT INTO tasks (" +
                    "id, sid, title, description, created_by, created_at, " +
                    "assignee, priority, status, due_date, version, deleted" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT (id) DO UPDATE SET " +
                    "sid = EXCLUDED.sid, " +
                    "title = EXCLUDED.title, " +
                    "description = EXCLUDED.description, " +
                    "created_by = EXCLUDED.created_by, " +
                    "created_at = EXCLUDED.created_at, " +
                    "assignee = EXCLUDED.assignee, " +
                    "priority = EXCLUDED.priority, " +
                    "status = EXCLUDED.status, " +
                    "due_date = EXCLUDED.due_date, " +
                    "version = EXCLUDED.version, " +
                    "deleted = EXCLUDED.deleted";

    public PostgresTaskSaveRepository(JdbcTemplate jdbcTemplate, TaskRecordMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public void save(List<Task> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            return;
        }

        List<TaskRecord> entities = tasks.stream()
                .map(mapper::toRecord)
                .toList();

        List<Object[]> batchArgs = new ArrayList<>(entities.size());
        for (TaskRecord e : entities) {
            Object[] params = new Object[]{
                    e.getId(),
                    e.getSid(),
                    e.getTitle(),
                    e.getDescription(),
                    e.getCreatedBy(),
                    e.getCreatedAt() == null ? null : Timestamp.from(e.getCreatedAt()),
                    e.getAssignee(),
                    e.getPriority(),
                    e.getStatus(),
                    e.getDueDate() == null ? null : Timestamp.from(e.getDueDate()),
                    e.getVersion(),
                    e.getDeleted()
            };
            batchArgs.add(params);
        }

        for (int i = 0; i < batchArgs.size(); i += BATCH_SIZE) {
            int end = Math.min(batchArgs.size(), i + BATCH_SIZE);
            List<Object[]> chunk = batchArgs.subList(i, end);
            jdbcTemplate.batchUpdate(UPSERT_SQL, chunk);
        }
    }
}
