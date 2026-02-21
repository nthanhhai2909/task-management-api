package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import io.github.nthanhhai2909.taskmanagement.internal.application.exception.PageSizeExceededException;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.ListTasksHandler;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresTaskFindRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TaskRecordMapper mapper;

    private static final int MAX_INFRA_PAGE_SIZE = 100;

    private static final Map<String, String> SORT_COLUMN_MAP;

    static {
        SORT_COLUMN_MAP = new HashMap<>();
        SORT_COLUMN_MAP.put("id", "id");
        SORT_COLUMN_MAP.put("createdAt", "created_at");
        SORT_COLUMN_MAP.put("title", "title");
        SORT_COLUMN_MAP.put("priority", "priority");
    }

    public PostgresTaskFindRepository(JdbcTemplate jdbcTemplate, TaskRecordMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    public List<Task> list(ListTasksHandler.ListTasksQuery query, int offset, int limit, String sort) {
        if (limit > MAX_INFRA_PAGE_SIZE) {
            throw new PageSizeExceededException(String.format("Requested page size %d exceeds infra max %d", limit, MAX_INFRA_PAGE_SIZE));
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id, sid, title, description, created_by, created_at, assignee, priority, status, due_date, version, deleted FROM tasks WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (query.getUserId() != null) {
            sb.append(" AND created_by = ?");
            params.add(query.getUserId());
        }

        if (query.getTitle() != null) {
            sb.append(" AND title ILIKE ?");
            params.add("%" + query.getTitle() + "%");
        }

        if (query.getDescription() != null) {
            sb.append(" AND description ILIKE ?");
            params.add("%" + query.getDescription() + "%");
        }

        if (query.getAssignee() != null) {
            sb.append(" AND assignee = ?");
            params.add(query.getAssignee());
        }

        if (query.getPriority() != null) {
            sb.append(" AND priority = ?");
            params.add(query.getPriority());
        }

        // sorting
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            String field = parts[0].trim();
            String dir = parts.length > 1 ? parts[1].trim().toUpperCase() : "ASC";
            String col = SORT_COLUMN_MAP.get(field);
            if (col != null && ("ASC".equals(dir) || "DESC".equals(dir))) {
                sb.append(" ORDER BY ").append(col).append(" ").append(dir);
            }
        }

        sb.append(" LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<TaskRecord> records = jdbcTemplate.query(sb.toString(), mapper, params.toArray());
        if (CollectionUtils.isEmpty(records)) {
            return new ArrayList<>();
        }
        return records.stream().map(mapper::toDomain).toList();
    }

    public long count(ListTasksHandler.ListTasksQuery query) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(*) FROM tasks WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (query.getUserId() != null) {
            sb.append(" AND created_by = ?");
            params.add(query.getUserId());
        }

        if (query.getTitle() != null) {
            sb.append(" AND title ILIKE ?");
            params.add("%" + query.getTitle() + "%");
        }

        if (query.getDescription() != null) {
            sb.append(" AND description ILIKE ?");
            params.add("%" + query.getDescription() + "%");
        }

        if (query.getAssignee() != null) {
            sb.append(" AND assignee = ?");
            params.add(query.getAssignee());
        }

        if (query.getPriority() != null) {
            sb.append(" AND priority = ?");
            params.add(query.getPriority());
        }

        Long count = jdbcTemplate.queryForObject(sb.toString(), params.toArray(), Long.class);
        return count == null ? 0L : count;
    }
}
