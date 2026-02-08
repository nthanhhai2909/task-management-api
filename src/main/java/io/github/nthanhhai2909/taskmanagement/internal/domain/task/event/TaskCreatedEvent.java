package io.github.nthanhhai2909.taskmanagement.internal.domain.task.event;

import io.github.nthanhhai2909.taskmanagement.internal.domain.AggregateType;
import io.github.nthanhhai2909.taskmanagement.internal.domain.Event;
import io.github.nthanhhai2909.taskmanagement.internal.domain.SID;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class TaskCreatedEvent implements Event<SID, TaskID> {
    private SID id;
    private TaskID taskId;
    private TaskTitle taskTitle;
    private TaskCreatedBy taskCreatedBy;
    private TaskCreatedAt taskCreatedAt;
    private TaskAssignee taskAssignee;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private TaskDueDate taskDueDate;
    private LocalDateTime occurredOn;

    // ------------------------------------ Constructor ------------------------------------
    public TaskCreatedEvent() {
    }

    // ------------------------------------ Getter methods ------------------------------------
    public TaskID taskId() {
        return taskId;
    }

    public TaskTitle taskTitle() {
        return taskTitle;
    }

    public TaskCreatedBy taskCreatedBy() {
        return taskCreatedBy;
    }

    public TaskCreatedAt taskCreatedAt() {
        return taskCreatedAt;
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
        return EventType.TASK_CREATED.type();
    }

    @Override
    public String description() {
        return EventType.TASK_CREATED.description();
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

    // ------------------------------------ Setter methods ------------------------------------

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

    public void taskCreatedBy(TaskCreatedBy createdBy) {
        this.taskCreatedBy = createdBy;
    }

    public void taskCreatedAt(TaskCreatedAt createdAt) {
        this.taskCreatedAt = createdAt;
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
