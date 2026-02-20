package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class TaskRecordMapper implements RowMapper<TaskRecord> {

    public TaskRecordMapper() {}

    public TaskRecord toRecord(Task task) {
        TaskRecord e = new TaskRecord();
        if (task.id() != null && task.id().id() != null) {
            e.setId(task.id().id());
        }
        e.setSid(task.id().sid());
        e.setTitle(task.title().stringValue());
        e.setDescription(task.description() == null ? null : task.description().stringValue());
        e.setCreatedBy(task.createdBy().stringValue());
        e.setCreatedAt(task.createdAt() == null ? Instant.now() : task.createdAt().toInstant());
        if (task.assignee() != null) e.setAssignee(task.assignee().stringValue());
        e.setPriority(task.priority() == null ? null : task.priority().stringValue());
        e.setStatus(task.status() == null ? null : task.status().stringValue());
        e.setDueDate(task.dueDate() == null ? null : task.dueDate().toInstant());
        e.setVersion(task.version());
        e.setDeleted(task.isDeleted());
        return e;
    }

    public Task toDomain(TaskRecord e) {
        TaskID id = TaskID.of(e.getId(), e.getSid());
        TaskTitle title = TaskTitle.of(e.getTitle());
        TaskDescription desc = e.getDescription() == null ? TaskDescription.of(null) : TaskDescription.of(e.getDescription());
        TaskCreatedBy createdBy = TaskCreatedBy.of(e.getCreatedBy());
        TaskCreatedAt createdAt = e.getCreatedAt() == null ? null : TaskCreatedAt.ofEpochMilli(e.getCreatedAt().toEpochMilli());
        TaskAssignee assignee = e.getAssignee() == null ? null : TaskAssignee.of(e.getAssignee());
        TaskPriority priority = e.getPriority() == null ? TaskPriority.of(TaskPriority.Priority.UNKNOWN) : TaskPriority.fromString(e.getPriority());
        TaskStatus status = e.getStatus() == null ? TaskStatus.of(TaskStatus.Status.UNKNOWN) : TaskStatus.fromString(e.getStatus());
        TaskDueDate due = e.getDueDate() == null ? null : TaskDueDate.ofEpochMilli(e.getDueDate().toEpochMilli());

        return Task.reconstitute(id, title, desc, createdBy, createdAt, assignee, priority, status, due, e.getVersion(), e.getDeleted());
    }

    @Override
    public TaskRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        TaskRecord e = new TaskRecord();
        e.setId(rs.getLong("id"));
        if (rs.wasNull()) e.setId(null);
        e.setSid(rs.getString("sid"));
        e.setTitle(rs.getString("title"));
        e.setDescription(rs.getString("description"));
        e.setCreatedBy(rs.getString("created_by"));
        java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
        e.setCreatedAt(createdAt == null ? null : createdAt.toInstant());
        e.setAssignee(rs.getString("assignee"));
        e.setPriority(rs.getString("priority"));
        e.setStatus(rs.getString("status"));
        java.sql.Timestamp due = rs.getTimestamp("due_date");
        e.setDueDate(due == null ? null : due.toInstant());
        e.setVersion(rs.getLong("version"));
        if (rs.wasNull()) e.setVersion(null);
        e.setDeleted(rs.getBoolean("deleted"));
        if (rs.wasNull()) e.setDeleted(null);
        return e;
    }
}
