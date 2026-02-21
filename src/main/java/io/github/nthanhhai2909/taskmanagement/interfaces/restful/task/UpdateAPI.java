package io.github.nthanhhai2909.taskmanagement.interfaces.restful.task;

import io.github.nthanhhai2909.taskmanagement.interfaces.restful.common.ErrorResponse;
import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.UpdateTaskHandler;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.UpdateTaskHandler.Result;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.UpdateTaskHandler.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class UpdateAPI {
    private static final Logger log = LoggerFactory.getLogger(UpdateAPI.class);

    private final UpdateTaskHandler updateHandler;

    public UpdateAPI(UpdateTaskHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    @PutMapping("/{sid}")
    public ResponseEntity<Object> updateBySid(@PathVariable("sid") String sid, @RequestBody UpdateRequest req) {
        try {
            Command cmd = Command.builder()
                    .sid(sid)
                    .title(req.title)
                    .description(req.description)
                    .assignee(req.assignee)
                    .priority(req.priority)
                    .status(req.status)
                    .dueDate(req.dueDate)
                    .build();

            Result res = updateHandler.execute(cmd);
            if (res == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Response body = Response.builder()
                    .id(res.getId().stringValue())
                    .title(res.getTitle().stringValue())
                    .description(res.getDescription().stringValue())
                    .createdBy(res.getCreatedBy().stringValue())
                    .createdAt(res.getCreatedAt().toEpochMilli())
                    .assignee(res.getAssignee().stringValue())
                    .priority(res.getPriority().stringValue())
                    .status(res.getStatus().stringValue())
                    .dueDate(res.getDueDate() == null ? null : res.getDueDate().toEpochMilli())
                    .build();
            return ResponseEntity.ok(body);
        } catch (IllegalArgumentException e) {
            log.error("Invalid update request for sid={}: {}", sid, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(e));
        } catch (DomainRuleViolationException e) {
            log.error("Domain rule violation while updating task {}: {}", sid, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(e));
        } catch (Exception e) {
            log.error("Unexpected error while updating task {}", sid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        private String title;
        private String description;
        private String assignee;
        private String priority;
        private String status;
        private java.time.Instant dueDate;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String id;
        private String title;
        private String description;
        private String createdBy;
        private Long createdAt;
        private String assignee;
        private String priority;
        private String status;
        private Long dueDate;
    }
}
