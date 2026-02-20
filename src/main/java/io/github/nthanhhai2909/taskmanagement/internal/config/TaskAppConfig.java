package io.github.nthanhhai2909.taskmanagement.internal.config;

import io.github.nthanhhai2909.taskmanagement.internal.application.db.IDGenerator;
import io.github.nthanhhai2909.taskmanagement.internal.application.db.TransactionManager;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.CreateTaskHandler;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.TaskRepository;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.GetTaskBySidHandler;
import io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.PostgresIDGenerator;
import io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task.PostgresTaskRepository;
import io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task.TaskRecordMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class TaskAppConfig {

    @Bean
    public IDGenerator taskIdGenerator(JdbcTemplate jdbcTemplate) {
        return new PostgresIDGenerator(jdbcTemplate, "task_id_seq");
    }

    @Bean
    public TaskRepository taskRepository(JdbcTemplate jdbcTemplate) {
        return new PostgresTaskRepository(jdbcTemplate, new TaskRecordMapper());
    }

    @Bean
    public CreateTaskHandler createTaskHandler(
            TaskRepository taskRepository,
            TransactionManager transactionManager,
            IDGenerator taskIdGenerator
    ) {
        return new CreateTaskHandler(taskRepository, transactionManager, taskIdGenerator);
    }

    @Bean
    public GetTaskBySidHandler getTaskBySidHandler(TaskRepository taskRepository) {
        return new GetTaskBySidHandler(taskRepository);
    }
}
