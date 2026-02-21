package io.github.nthanhhai2909.taskmanagement.interfaces.restful.task;

import io.github.nthanhhai2909.taskmanagement.interfaces.restful.common.ErrorResponse;
import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.GetTaskBySidHandler;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.GetTaskBySidHandler.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class GetBySID {
    private static final Logger log = LoggerFactory.getLogger(GetBySID.class);

    private final GetTaskBySidHandler handler;

    public GetBySID(GetTaskBySidHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/{sid}")
    public ResponseEntity<Object> getBySid(@PathVariable String sid) {
        try {
            Result res = handler.execute(sid);
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
            log.error("Invalid request for sid={}: {}", sid, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(e));
        } catch (DomainRuleViolationException e) {
            log.error("Domain rule violation while querying task {}: {}", sid, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(e));
        } catch (Exception e) {
            log.error("Unexpected error while querying task {}", sid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
