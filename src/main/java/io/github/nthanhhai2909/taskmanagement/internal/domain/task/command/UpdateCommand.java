package io.github.nthanhhai2909.taskmanagement.internal.domain.task.command;

import io.github.nthanhhai2909.taskmanagement.internal.domain.task.*;

public class UpdateCommand {
    private final TaskTitle title;
    private final TaskDescription description;
    private final TaskAssignee assignee;
    private final TaskPriority priority;
    private final TaskStatus status;
    private final TaskDueDate dueDate;

    public UpdateCommand(TaskTitle title, TaskDescription description, TaskAssignee assignee, TaskPriority priority, TaskStatus status, TaskDueDate dueDate) {
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
    }

    public TaskTitle getTitle() {
        return title;
    }

    public TaskDescription getDescription() {
        return description;
    }

    public TaskAssignee getAssignee() {
        return assignee;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskDueDate getDueDate() {
        return dueDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TaskTitle title;
        private TaskDescription description;
        private TaskAssignee assignee;
        private TaskPriority priority;
        private TaskStatus status;
        private TaskDueDate dueDate;

        public Builder title(TaskTitle title) {
            this.title = title;
            return this;
        }

        public Builder description(TaskDescription description) {
            this.description = description;
            return this;
        }

        public Builder assignee(TaskAssignee assignee) {
            this.assignee = assignee;
            return this;
        }

        public Builder priority(TaskPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Builder dueDate(TaskDueDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public UpdateCommand build() {
            return new UpdateCommand(title, description, assignee, priority, status, dueDate);
        }
    }
}
