package com.nab.dev.taskmanagement.internal.domain.task;

import com.nab.dev.taskmanagement.internal.domain.AggregateRoot;
import com.nab.dev.taskmanagement.internal.domain.AggregateType;
import com.nab.dev.taskmanagement.internal.domain.DomainException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements AggregateRoot<TaskID> {
    private TaskID id;
    private TaskTitle title;
    private TaskDescription description;
    private TaskCreatedBy createdBy;
    private TaskCreatedAt createdAt;
    private TaskAssignee assignee;
    private TaskPriority priority;
    private TaskStatus status;
    private TaskDueDate dueDate;

    @Override
    public TaskID id() {
        return this.id;
    }

    @Override
    public AggregateType aggregateType() {
        return AggregateType.TASK;
    }

    @Override
    public void validate() throws DomainException {
        this.id.validate();
        this.title.validate();
        this.description.validate();
        this.createdBy.validate();
        this.createdAt.validate();
        this.assignee.validate();
        this.priority.validate();
        this.status.validate();
        this.dueDate.validate();
    }
}
