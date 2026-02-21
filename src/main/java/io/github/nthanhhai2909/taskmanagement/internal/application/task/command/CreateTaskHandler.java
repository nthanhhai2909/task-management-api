package io.github.nthanhhai2909.taskmanagement.internal.application.task.command;

import io.github.nthanhhai2909.taskmanagement.internal.application.db.GeneratedId;
import io.github.nthanhhai2909.taskmanagement.internal.application.db.IDGenerator;
import io.github.nthanhhai2909.taskmanagement.internal.application.db.TransactionManager;
import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;
public class CreateTaskHandler {
    private final TaskCommandRepository taskCommandRepository;
    private final TransactionManager tx;
    private final IDGenerator idGenerator;

    public CreateTaskHandler(
            TaskCommandRepository taskCommandRepository,
            TransactionManager tx,
            IDGenerator idGenerator
    ) {
        this.taskCommandRepository = taskCommandRepository;
        this.tx = tx;
        this.idGenerator = idGenerator;
    }

    /*
     * Execute the command to create a new task.
     * @param command the command to create a new task
     * @return the result of the command execution, containing the created task's information
     * @throws @DomainRuleViolationException if the command is null, or if any domain rule is violated during task creation, such as missing required fields or invalid values
     */
    public Result execute(Command command) {
        try {
            if (command == null) {
                throw new DomainRuleViolationException(400001, "Command must not be null");
            }

            // generate id pair (numeric + sid) from infra
            GeneratedId gid = idGenerator.get();

            Task task = Task.Builder.builder()
                    .id(TaskID.of(gid.id(), gid.sid()))
                    .title(TaskTitle.of(command.getTitle()))
                    .description(TaskDescription.of(command.getDescription()))
                    .createdBy(TaskCreatedBy.of(command.getCreatedBy()))
                    .assignee(TaskAssignee.of(command.getAssignee()))
                    .createdAt(TaskCreatedAt.now())
                    .priority(TaskPriority.fromString(command.getPriority()))
                    .status(TaskStatus.fromString(command.getStatus()))
                    .dueDate(TaskDueDate.of(command.getDueDate()))
                    .build();

            this.tx.required(() -> this.taskCommandRepository.save(Collections.singletonList(task)));

            return Result.builder()
                    .id(task.id())
                    .title(task.title())
                    .description(task.description())
                    .createdBy(task.createdBy())
                    .createdAt(task.createdAt())
                    .assignee(task.assignee())
                    .priority(task.priority())
                    .status(task.status())
                    .dueDate(task.dueDate())
                    .build();
        } catch (DomainException ex) {
            throw new DomainRuleViolationException(ex.code(), ex.description());
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static final class Command {
        private String title;
        private String description;
        private String createdBy;
        private String assignee;
        private String priority;
        private String status;
        private Instant dueDate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static final class Result {
        private TaskID id;
        private TaskTitle title;
        private TaskDescription description;
        private TaskCreatedBy createdBy;
        private TaskCreatedAt createdAt;
        private TaskAssignee assignee;
        private TaskPriority priority;
        private TaskStatus status;
        private TaskDueDate dueDate;
    }
}
