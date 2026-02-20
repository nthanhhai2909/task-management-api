package io.github.nthanhhai2909.taskmanagement.internal.domain.task.event;

import io.github.nthanhhai2909.taskmanagement.internal.domain.AggregateType;
import io.github.nthanhhai2909.taskmanagement.internal.domain.Event;
import io.github.nthanhhai2909.taskmanagement.internal.domain.SID;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class TaskUpdatedEvent implements Event<SID, TaskID> {
    private SID id;
    private TaskID taskId;
    private TaskTitle taskTitle;
    private TaskDescription taskDescription;
    private TaskAssignee taskAssignee;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private TaskDueDate taskDueDate;
    private LocalDateTime occurredOn;

    public TaskUpdatedEvent() {
    }

    public TaskID taskId() {
        return taskId;
    }

    public TaskTitle taskTitle() {
        return taskTitle;
    }

    public TaskDescription taskDescription() {
        return taskDescription;
    }

    public TaskAssignee taskAssignee() {
        return taskAssignee;
    }

    public TaskPriority taskPriority() {
        return taskPriority;
    }

    public TaskStatus taskStatus() {
        return taskStatus;
    }

    public Optional<TaskDueDate> taskDueDate() {
        return Optional.ofNullable(taskDueDate);
    }

    @Override
    public SID id() {
        return this.id;
    }

    @Override
    public String type() {
        return EventType.TASK_UPDATED.type();
    }

    @Override
    public String description() {
        return EventType.TASK_UPDATED.description();
    }

    @Override
    public LocalDateTime occurredOn() {
        return this.occurredOn;
    }

    @Override
    public AggregateType aggregateType() {
        return AggregateType.TASK;
    }

    @Override
    public TaskID aggregateId() {
        return this.taskId;
    }

    public void id(SID id) {
        this.id = id;
    }

    public void occurredOn(LocalDateTime occurredOn) {
        this.occurredOn = occurredOn;
    }

    public void taskId(TaskID taskId) {
        this.taskId = taskId;
    }

    public void taskTitle(TaskTitle taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void taskDescription(TaskDescription taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void taskAssignee(TaskAssignee assignee) {
        this.taskAssignee = assignee;
    }

    public void taskPriority(TaskPriority priority) {
        this.taskPriority = priority;
    }

    public void taskStatus(TaskStatus status) {
        this.taskStatus = status;
    }

    public void taskDueDate(TaskDueDate dueDate) {
        this.taskDueDate = dueDate;
    }
}

