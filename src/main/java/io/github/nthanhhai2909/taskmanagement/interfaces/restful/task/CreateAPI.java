package io.github.nthanhhai2909.taskmanagement.interfaces.restful.task;

import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.CreateTaskHandler;
import io.github.nthanhhai2909.taskmanagement.interfaces.restful.common.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class CreateAPI {
    private final CreateTaskHandler createTaskHandler;

    public CreateAPI(CreateTaskHandler createTaskHandler) {
        this.createTaskHandler = createTaskHandler;
    }

    /**
     * Creates a new task.
     * @return 201 with {@link Response} on success, or 400 with {@link io.github.nthanhhai2909.taskmanagement.interfaces.restful.common.ErrorResponse} on domain rule violation
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Request req) {
        try {
            CreateTaskHandler.Command command = new CreateTaskHandler.Command(
                    req.getTitle(),
                    req.getDescription(),
                    req.getCreatedBy(),
                    req.getAssignee(),
                    req.getPriority(),
                    req.getStatus(),
                    Instant.ofEpochMilli(req.getDueDate()).atZone(ZoneOffset.UTC).toLocalDateTime()
            );
            CreateTaskHandler.Result result = this.createTaskHandler.execute(command);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Response.builder()
                            .id(result.getId().stringValue())
                            .title(result.getTitle().stringValue())
                            .description(result.getDescription().stringValue())
                            .createdBy(result.getCreatedBy().stringValue())
                            .assignee(result.getAssignee().stringValue())
                            .priority(result.getPriority().stringValue())
                            .status(result.getStatus().stringValue())
                            .dueDate(result.getDueDate().toEpochMilli())
                            .createdAt(result.getCreatedAt().toEpochMilli())
                            .build());
        } catch (DomainRuleViolationException e) {
            log.error("Domain rule violation during task creation: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder()
                            .code(e.code())
                            .message(e.description())
                            .build());
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Request {
        private String title;
        private String description;
        private String createdBy;
        private String assignee;
        private String priority;
        private String status;
        private long dueDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class ErrorResponse {
        private String code;
        private String message;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response {
        private String id;
        private String title;
        private String description;
        private String createdBy;
        private String assignee;
        private String priority;
        private String status;
        private Long dueDate;
        private Long createdAt;
    }
}
