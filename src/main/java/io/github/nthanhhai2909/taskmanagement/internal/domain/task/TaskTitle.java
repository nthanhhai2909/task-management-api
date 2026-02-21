package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.ValueObject;
import org.apache.commons.lang3.StringUtils;

public class TaskTitle implements ValueObject {
    private static final int MAX_LEN = 500;
    private final String title;

    TaskTitle(String title) {
        this.title = title;
    }

    public static TaskTitle of(String title) {
        return new TaskTitle(title);
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(this.title);
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public String stringValue() {
        return this.title;
    }

    @Override
    public void validate() throws DomainException {
        if (StringUtils.isBlank(this.title)) {
            throw new DomainException(Error.TASK_TITLE_REQUIRED.description());
        }

        if (this.title.length() > MAX_LEN) {
            throw new DomainException(String.format(Error.TASK_TITLE_TOO_LONG.description(), MAX_LEN, this.title.length()));
        }
    }
}
