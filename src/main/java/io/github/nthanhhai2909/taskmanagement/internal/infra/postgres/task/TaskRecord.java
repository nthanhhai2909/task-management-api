package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskRecord {

    private Long id;

    private String sid;

    private String title;

    private String description;

    private String createdBy;

    private Instant createdAt;

    private String assignee;

    private String priority;

    private String status;

    private Instant dueDate;

    private Long version;

    private Boolean deleted = false;
}

