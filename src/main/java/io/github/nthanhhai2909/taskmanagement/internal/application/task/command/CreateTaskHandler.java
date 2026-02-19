package io.github.nthanhhai2909.taskmanagement.internal.application.task.command;

import io.github.nthanhhai2909.taskmanagement.internal.application.db.IDGenerator;
import io.github.nthanhhai2909.taskmanagement.internal.application.db.TransactionManager;
import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskAssignee;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskCreatedAt;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskCreatedBy;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskDescription;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskDueDate;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskPriority;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskStatus;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskTitle;

import java.time.LocalDateTime;
import java.util.Collections;



public class CreateTaskHandler {
    private final TaskRepository taskRepository;
    private final TransactionManager tx;
    private final IDGenerator idGenerator;

    public CreateTaskHandler(
            TaskRepository taskRepository,
            TransactionManager tx,
            IDGenerator idGenerator
    ) {
        this.taskRepository = taskRepository;
        this.tx = tx;
        this.idGenerator = idGenerator;
    }

    /*
     * Execute the command to create a new task.
     * @param command the command to create a new task
     * @return the result of the command execution, containing the created task's information
     * @throws @IllegalArgumentException if the command is null
     * @throws @DomainRuleViolationException if any domain rule is violated during task creation, such as missing required fields or invalid values
     */
    public Result execute(Command command) {
        try {
            if (command == null) {
                throw new IllegalArgumentException("Command must not be null");
            }
            Task task = Task.Builder.builder()
                    .id(TaskID.of(this.idGenerator.get()))
                    .title(TaskTitle.of(command.getTitle()))
                    .description(TaskDescription.of(command.getDescription()))
                    .createdBy(TaskCreatedBy.of(command.getCreatedBy()))
                    .assignee(TaskAssignee.of(command.getAssignee()))
                    .createdAt(TaskCreatedAt.now())
                    .priority(TaskPriority.fromString(command.getPriority()))
                    .status(TaskStatus.fromString(command.getStatus()))
                    .dueDate(TaskDueDate.of(command.getDueDate()))
                    .build();
            this.tx.required(() -> this.taskRepository.save(Collections.singletonList(task)));
            return Result.fromTask(task);
        } catch (DomainException ex) {
            throw new DomainException(ex.code(), ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public static final class Command {
        private String title;
        private String description;
        private String createdBy;
        private String assignee;
        private String priority;
        private String status;
        private LocalDateTime dueDate;

        public Command() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getDueDate() {
            return dueDate;
        }

        public void setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
        }
    }

    public static final class Result {
        public Result() {
        }

        private TaskID id;
        private TaskTitle title;
        private TaskDescription description;
        private TaskCreatedBy createdBy;
        private TaskCreatedAt createdAt;
        private TaskAssignee assignee;
        private TaskPriority priority;
        private TaskStatus status;
        private TaskDueDate dueDate;

        public static Result fromTask(Task task) {
            Result result = new Result();
            result.id = task.id();
            result.title = task.title();
            result.description = task.description();
            result.createdBy = task.createdBy();
            result.createdAt = task.createdAt();
            result.assignee = task.assignee();
            result.priority = task.priority();
            result.status = task.status();
            result.dueDate = task.dueDate();
            return result;
        }

        public TaskID getId() {
            return id;
        }

        public void setId(TaskID id) {
            this.id = id;
        }

        public TaskTitle getTitle() {
            return title;
        }

        public void setTitle(TaskTitle title) {
            this.title = title;
        }

        public TaskDescription getDescription() {
            return description;
        }

        public void setDescription(TaskDescription description) {
            this.description = description;
        }

        public TaskCreatedBy getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(TaskCreatedBy createdBy) {
            this.createdBy = createdBy;
        }

        public TaskCreatedAt getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(TaskCreatedAt createdAt) {
            this.createdAt = createdAt;
        }

        public TaskAssignee getAssignee() {
            return assignee;
        }

        public void setAssignee(TaskAssignee assignee) {
            this.assignee = assignee;
        }

        public TaskPriority getPriority() {
            return priority;
        }

        public void setPriority(TaskPriority priority) {
            this.priority = priority;
        }

        public TaskStatus getStatus() {
            return status;
        }

        public void setStatus(TaskStatus status) {
            this.status = status;
        }

        public TaskDueDate getDueDate() {
            return dueDate;
        }

        public void setDueDate(TaskDueDate dueDate) {
            this.dueDate = dueDate;
        }
    }
}
