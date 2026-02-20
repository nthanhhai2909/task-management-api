package io.github.nthanhhai2909.taskmanagement.internal.application.task.query;

import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.TaskRepository;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskID;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskCreatedAt;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskDueDate;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskTitle;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskDescription;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskCreatedBy;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskAssignee;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskPriority;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class GetTaskBySidHandler {
    private final TaskRepository taskRepository;

    public GetTaskBySidHandler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Result execute(String sid) {
        if (StringUtils.isBlank(sid)) {
            throw new IllegalArgumentException("sid_required");
        }

        Optional<Task> taskOpt = taskRepository.findById(TaskID.of(sid));
        if (taskOpt.isEmpty()) {
            return null;
        }

        Task task = taskOpt.get();

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
