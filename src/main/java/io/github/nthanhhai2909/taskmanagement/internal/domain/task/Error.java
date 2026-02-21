package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

public enum Error {
    TASK_ID_REQUIRED("Id must be provided"),
    TASK_ASSIGNEE_REQUIRED("Assignee must be provided"),
    TASK_CREATED_BY_REQUIRED("CreatedBy must be provided"),
    TASK_CREATED_AT_REQUIRED("CreatedAt must be provided"),
    TASK_CREATED_AT_MUST_NOT_BE_IN_FUTURE("CreatedAt must equal or before current time"),
    TASK_TITLE_REQUIRED("Title must be provided"),
    TASK_TITLE_TOO_LONG("Title must be at most %d characters (got %d)"),
    TASK_DESCRIPTION_REQUIRED("Description must be provided"),
    TASK_DESCRIPTION_TOO_LONG("Description must be at most %d characters (got %d)"),
    TASK_STATUS_REQUIRED("Status must be provided"),
    TASK_DUE_DATE_MUST_BE_IN_FUTURE("DueDate must equal or after current time"),
    TASK_PRIORITY_REQUIRED("Priority must be provided"),
    TASK_PRIORITY_INVALID("Priority is invalid: %s");

    private final String description;

    Error(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
