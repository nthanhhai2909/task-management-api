package io.github.nthanhhai2909.taskmanagement.internal.application.task.query;

import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.Task;
import io.github.nthanhhai2909.taskmanagement.internal.domain.task.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ListTasksHandler {
    private static final int APP_MAX_PAGE_SIZE = 100;

    private final TaskQueryRepository repo;

    public ListTasksHandler(TaskQueryRepository repo) {
        this.repo = repo;
    }

    public ListTasksResult execute(ListTasksQuery q) {
        if (q == null) {
            throw new DomainRuleViolationException(400100, "Query must not be null");
        }

        int page = q.getPage();
        int size = q.getSize();

        if (size <= 0) {
            throw new DomainRuleViolationException(400101, "Size must be positive");
        }

        if (size > APP_MAX_PAGE_SIZE) {
            throw new DomainRuleViolationException(400102, String.format("Requested size %d exceeds application max %d", size, APP_MAX_PAGE_SIZE));
        }

        TaskPriority priority = TaskPriority.fromString(q.getPriority());

        int offset = page * size;

        List<Task> tasks = repo.list(q, offset, size, q.getSort());
        long total = repo.count(q);

        List<ListTasksResult.Item> items = tasks.stream().map(t -> ListTasksResult.Item.builder()
                .id(t.id().lid() == null ? null : String.valueOf(t.id().lid()))
                .sid(t.id().sid())
                .title(t.title().stringValue())
                .description(t.description().stringValue())
                .createdBy(t.createdBy().stringValue())
                .createdAt(t.createdAt() == null ? null : t.createdAt().toEpochMilli())
                .assignee(t.assignee() == null ? null : t.assignee().stringValue())
                .priority(t.priority() == null ? null : t.priority().stringValue())
                .status(t.status() == null ? null : t.status().stringValue())
                .dueDate(t.dueDate() == null ? null : t.dueDate().toEpochMilli())
                .build()).toList();

        return ListTasksResult.builder()
                .total(total)
                .page(page)
                .size(size)
                .items(items)
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ListTasksQuery {
        private String userId;
        private String title;
        private String description;
        private String assignee;
        private String priority;
        private int page;
        private int size;
        private String sort;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ListTasksResult {
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
