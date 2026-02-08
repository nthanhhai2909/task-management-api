package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;
import org.apache.commons.lang3.StringUtils;

public class TaskAssignee implements ValueObject {
    private final String userId;

    TaskAssignee(String userId) {
        this.userId = userId;
    }

    public static TaskAssignee of(String userId) {
        return new TaskAssignee(userId);
    }

    public String stringValue() {
        return this.userId;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(this.userId);
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public boolean isEqual(TaskAssignee other) {
        return this.userId.equals(other.userId);
    }

    public boolean isNotEqual(TaskAssignee other) {
        return !this.isEqual(other);
    }

    @Override
    public void validate() throws DomainException {
        if (StringUtils.isBlank(this.userId)) {
            throw new DomainException(Error.TASK_ASSIGNEE_REQUIRED.code(), Error.TASK_ASSIGNEE_REQUIRED.description());
        }
    }
}
