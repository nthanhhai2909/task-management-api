package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.TaskRepository;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresTaskRepository implements TaskRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TaskRecordMapper mapper;

    // Chunk size for batch updates to avoid extremely large batches
    private static final int BATCH_SIZE = 500;

    // Upsert SQL using Postgres ON CONFLICT on primary key (id)
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

    public PostgresTaskRepository(JdbcTemplate jdbcTemplate, TaskRecordMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public void save(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
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

    @Override
    public Optional<Task> findById(TaskID id) {
        if (id == null || (id.id() == null && (id.sid() == null || id.sid().isEmpty()))) {
            return Optional.empty();
        }

        // prefer lookup by numeric id when available
        try {
            List<TaskRecord> results;
            if (id.id() != null) {
                results = jdbcTemplate.query(SELECT_BY_ID_SQL, mapper, id.id());
            } else {
                results = jdbcTemplate.query(SELECT_BY_SID_SQL, mapper, id.sid());
            }
            if (results.isEmpty()) return Optional.empty();
            return Optional.ofNullable(mapper.toDomain(results.get(0)));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
