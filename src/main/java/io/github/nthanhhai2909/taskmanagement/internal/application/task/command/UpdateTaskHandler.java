package io.github.nthanhhai2909.taskmanagement.internal.application.task.command;

import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.DomainException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.*;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.command.UpdateCommand;
import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

public class UpdateTaskHandler {
    private final TaskRepository taskRepository;

    public UpdateTaskHandler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Result execute(Command command) {
        try {
            if (command == null || StringUtils.isBlank(command.getSid())) {
                throw new DomainRuleViolationException(400001, "sid_required");
            }

            Optional<Task> existingOpt = taskRepository.findById(TaskID.of(command.getSid()));
            if (existingOpt.isEmpty()) {
                return null;
            }

            Task existing = existingOpt.get();

            // Build domain UpdateCommand using domain value objects (null means keep existing)
            UpdateCommand.Builder domainCmdBuilder = UpdateCommand.builder();

            if (command.getTitle() != null) {
                domainCmdBuilder.title(TaskTitle.of(command.getTitle()));
            }
            if (command.getDescription() != null) {
                domainCmdBuilder.description(TaskDescription.of(command.getDescription()));
            }
            if (command.getAssignee() != null) {
                domainCmdBuilder.assignee(TaskAssignee.of(command.getAssignee()));
            }
            if (command.getPriority() != null){
                domainCmdBuilder.priority(TaskPriority.fromString(command.getPriority()));
            }
            if (command.getStatus() != null) {
                domainCmdBuilder.status(TaskStatus.fromString(command.getStatus()));
            }
            if (command.getDueDate() != null) {
                domainCmdBuilder.dueDate(TaskDueDate.of(command.getDueDate()));
            }

            UpdateCommand domainCmd = domainCmdBuilder.build();

            // Use aggregate's update method with the domain command
            existing.update(domainCmd);

            // Persist changes (aggregate has new version and events)
            this.taskRepository.save(Collections.singletonList(existing));

            return Result.builder()
                    .id(existing.id())
                    .title(existing.title())
                    .description(existing.description())
                    .createdBy(existing.createdBy())
                    .createdAt(existing.createdAt())
                    .assignee(existing.assignee())
                    .priority(existing.priority())
                    .status(existing.status())
                    .dueDate(existing.dueDate())
                    .build();
        } catch (DomainException ex) {
            throw new DomainRuleViolationException(ex.code(), ex.description());
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static final class Command {
        private String sid;
        private String title;
        private String description;
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
