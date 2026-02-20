package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

public enum Error {
    TASK_ID_REQUIRED(400001, "Id must be provided"),
    TASK_ASSIGNEE_REQUIRED(400002, "Assignee must be provided"),
    TASK_CREATED_BY_REQUIRED(400003, "CreatedBy must be provided"),
    TASK_CREATED_AT_REQUIRED(400004, "CreatedAt must be provided"),
    TASK_CREATED_AT_MUST_NOT_BE_IN_FUTURE(400005, "CreatedAt must equal or before current time"),
    TASK_TITLE_REQUIRED(400006, "Title must be provided"),
    TASK_TITLE_TOO_LONG(400007, "Title must be at most %d characters (got %d)"),
    TASK_DESCRIPTION_REQUIRED(400008, "Description must be provided"),
    TASK_DESCRIPTION_TOO_LONG(400009, "Description must be at most %d characters (got %d)"),
    TASK_STATUS_REQUIRED(400010, "Status must be provided"),
    TASK_DUE_DATE_MUST_BE_IN_FUTURE(400011, "DueDate must equal or after current time"),
    TASK_PRIORITY_REQUIRED(400012, "Priority must be provided"),
    TASK_PRIORITY_INVALID(400013, "Priority is invalid: %s");

    private final int code;
    private final String description;

   Error(int code, String description) {
       this.code = code;
       this.description = description;
   }

    public int code() {
       return code;
    }

    public String description() {
       return description;
    }
}
