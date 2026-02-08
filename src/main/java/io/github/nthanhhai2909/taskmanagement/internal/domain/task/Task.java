package io.github.nthanhhai2909.taskmanagement.internal.domain.task;

import io.github.nthanhhai2909.taskmanagement.internal.domain.*;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.event.TaskCreatedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Task implements AggregateRoot<TaskID> {
    private TaskID id;
    private TaskTitle taskTitle;
    private TaskDescription description;
    private TaskCreatedBy createdBy;
    private TaskCreatedAt createdAt;
    private TaskAssignee assignee;
    private TaskPriority priority;
    private TaskStatus status;
    private TaskDueDate dueDate;
    private List<Event<?, ?>> events = new ArrayList<>();
    private long version;
    private boolean deleted;

    // ------------------------------------ Constructor ------------------------------------
    private Task() {
    }

    // ------------------------------------ Getter methods ------------------------------------
    @Override
    public TaskID id() {
        return this.id;
    }

    @Override
    public AggregateType aggregateType() {
        return AggregateType.TASK;
    }

    public TaskTitle title() {
        return this.taskTitle;
    }

    public TaskDescription description() {
        return this.description;
    }

    public TaskCreatedBy createdBy() {
        return this.createdBy;
    }

    public TaskCreatedAt createdAt() {
        return this.createdAt;
    }

    public TaskAssignee assignee() {
        return this.assignee;
    }

    public TaskPriority priority() {
        return this.priority;
    }

    public TaskStatus status() {
        return this.status;
    }

    public TaskDueDate dueDate() {
        return this.dueDate;
    }

    public long version() {
        return this.version;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    // ------------------------------------ Event methods ------------------------------------
    private void raise(Event<?, ?> event) {
        if (event == null) {
            return;
        }
        this.events.add(event);
    }

    public List<Event<?, ?>> pullEvents() {
        List<Event<?, ?>> copy = new ArrayList<>(this.events);
        this.events.clear();
        return Collections.unmodifiableList(copy);
    }

    public Optional<TaskCreatedEvent> createdEvent() {
        return this.events.stream()
                .filter(e -> e instanceof TaskCreatedEvent)
                .map(e -> (TaskCreatedEvent) e)
                .findFirst();
    }


    // ----------------------------------- Validation methods ----------------------------------
    @Override
    public void validate() throws DomainException {
        this.id.validate();
        this.taskTitle.validate();
        this.description.validate();
        this.createdBy.validate();
        this.createdAt.validate();
        this.assignee.validate();
        this.priority.validate();
        this.status.validate();
        this.dueDate.validate();
    }

    // ------------------------------------------- Builder  -------------------------------------------
    public static final class Builder {
        private final Task task;

        private Builder() {
            this.task = new Task();
        }

        public Builder builder() {
            return new Builder();
        }

        public Builder id(TaskID id) {
            this.task.id = id;
            return this;
        }

        public Builder title(TaskTitle title) {
            this.task.taskTitle = title;
            return this;
        }

        public Builder description(TaskDescription description) {
            this.task.description = description;
            return this;
        }

        public Builder createdAt(TaskCreatedAt createdAt) {
            this.task.createdAt = createdAt;
            return this;
        }

        public Builder createdBy(TaskCreatedBy createdBy) {
            this.task.createdBy = createdBy;
            return this;
        }

        public Builder assignee(TaskAssignee assignee) {
            this.task.assignee = assignee;
            return this;
        }

        public Builder priority(TaskPriority priority) {
            this.task.priority = priority;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.task.status = status;
            return this;
        }

        public Builder dueDate(TaskDueDate dueDate) {
            this.task.dueDate = dueDate;
            return this;
        }

        public Task build() throws DomainException {
            this.task.version = 0;
            this.task.deleted = false;
            this.task.events.clear();

            TaskCreatedEvent createdEvent = new TaskCreatedEvent();
            createdEvent.id(SID.random());
            createdEvent.occurredOn(LocalDateTime.now());
            createdEvent.taskId(this.task.id);
            createdEvent.taskTitle(this.task.taskTitle);
            createdEvent.taskCreatedBy(this.task.createdBy);
            createdEvent.taskCreatedAt(this.task.createdAt);
            createdEvent.taskAssignee(this.task.assignee);
            createdEvent.taskPriority(this.task.priority);
            createdEvent.taskStatus(this.task.status);
            createdEvent.taskDueDate(this.task.dueDate);
            this.task.raise(createdEvent);

            this.task.validate();
            return this.task;
        }
    }
}
