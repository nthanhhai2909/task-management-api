package io.github.nthanhhai2909.taskmanagement.interfaces.restful.task;

import io.github.nthanhhai2909.taskmanagement.interfaces.restful.common.ErrorResponse;
import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
import io.github.nthanhhai2909.taskmanagement.internal.application.exception.PageSizeExceededException;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.ListTasksHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class ListAPI {
    private static final int API_MAX_PAGE_SIZE = 50;

    private final ListTasksHandler handler;

    public ListAPI(ListTasksHandler handler) {
        this.handler = handler;
    }

    @GetMapping
    public ResponseEntity<Object> list(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        try {
            if (page < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().code(400201).message("page must be >= 0").build());
            }

            if (size <= 0 || size > API_MAX_PAGE_SIZE) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().code(400202).message("size must be between 1 and " + API_MAX_PAGE_SIZE).build());
            }

            ListTasksHandler.ListTasksQuery q = ListTasksHandler.ListTasksQuery.builder()
                    .userId(userId)
                    .title(title)
                    .description(description)
                    .assignee(assignee)
                    .priority(priority)
                    .page(page)
                    .size(size)
                    .sort(sort)
                    .build();

            ListTasksHandler.ListTasksResult result = this.handler.execute(q);

            // Map application result to API-specific response so we don't leak internal types
            List<ListTasksResponse.Item> items = result.getItems().stream().map(i -> ListTasksResponse.Item.builder()
                    .id(i.getId())
                    .sid(i.getSid())
                    .title(i.getTitle())
                    .description(i.getDescription())
                    .createdBy(i.getCreatedBy())
                    .createdAt(i.getCreatedAt())
                    .assignee(i.getAssignee())
                    .priority(i.getPriority())
                    .status(i.getStatus())
                    .dueDate(i.getDueDate())
                    .build()).collect(Collectors.toList());

            ListTasksResponse response = ListTasksResponse.builder()
                    .total(result.getTotal())
                    .page(result.getPage())
                    .size(result.getSize())
                    .items(items)
                    .build();

            return ResponseEntity.ok(response);
        } catch (DomainRuleViolationException e) {
            log.error("Domain rule violation while listing tasks: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().code(e.code()).message(e.description()).build());
        } catch (PageSizeExceededException e) {
            log.error("Page size exceeded in infra while listing tasks: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().code(e.code()).message(e.description()).build());
        } catch (Exception e) {
            log.error("Unexpected error while listing tasks", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ListTasksResponse {
        private long total;
        private int page;
        private int size;
        private List<Item> items;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Builder
        public static final class Item {
            private String id;
            private String sid;
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
}
