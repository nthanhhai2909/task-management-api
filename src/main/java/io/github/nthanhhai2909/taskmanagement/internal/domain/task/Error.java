package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

public enum Error {
    TASK_ID_REQUIRED("task_id_required", "Id must be provided"),
    TASK_ASSIGNEE_REQUIRED("task_assignee_required", "Assignee must be provided"),
    TASK_CREATED_BY_REQUIRED("task_created_by_required", "CreatedBy must be provided"),
    TASK_CREATED_AT_REQUIRED("task_created_at_required", "CreatedAt must be provided"),
    TASK_CREATED_AT_MUST_NOT_BE_IN_FUTURE("task_created_at_must_not_be_in_future", "CreatedAt must equal or before current time"),
    TASK_TITLE_REQUIRED("task_title_required", "Title must be provided"),
    TASK_TITLE_TOO_LONG("task_title_too_long", "Title must be at most %d characters (got %d)"),
    TASK_DESCRIPTION_REQUIRED("task_description_required", "Description must be provided"),
    TASK_DESCRIPTION_TOO_LONG("task_description_too_long", "Description must be at most %d characters (got %d)"),
    TASK_STATUS_REQUIRED("task_status_required", "Status must be provided"),
    TASK_DUE_DATE_MUST_BE_IN_FUTURE("task_due_date_must_be_in_future", "DueDate must equal or after current time"),
    TASK_PRIORITY_REQUIRED("task_priority_required", "Priority must be provided"),
    TASK_PRIORITY_INVALID("task_priority_invalid", "Priority is invalid: %s");

    private final String code;
    private final String description;

   Error(String code, String description) {
       this.code = code;
       this.description = description;
   }

    public String code() {
       return code;
    }

    public String description() {
       return description;
    }
}
