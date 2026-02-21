package io.github.nthanhhai2909.taskmanagement.internal.config;

import io.github.nthanhhai2909.taskmanagement.internal.application.db.IDGenerator;
import io.github.nthanhhai2909.taskmanagement.internal.application.db.TransactionManager;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.CreateTaskHandler;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.TaskCommandRepository;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.command.UpdateTaskHandler;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.GetTaskBySidHandler;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.ListTasksHandler;
import io.github.nthanhhai2909.taskmanagement.internal.application.task.query.TaskQueryRepository;
import io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.PostgresIDGenerator;
import io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task.PostgresTaskCommandRepository;
import io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task.PostgresTaskFindRepository;
import io.github.nthanhhai2909.taskmanagement.internal.infra.postgres.task.PostgresTaskQueryRepository;
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
    public TaskCommandRepository taskCommandRepository(JdbcTemplate jdbcTemplate) {
        return new PostgresTaskCommandRepository(jdbcTemplate, TaskRecordMapper.getInstance());
    }

    @Bean
    public TaskQueryRepository taskQueryRepository(JdbcTemplate jdbcTemplate) {
        return new PostgresTaskQueryRepository(jdbcTemplate, TaskRecordMapper.getInstance());
    }

    @Bean
    public CreateTaskHandler createTaskHandler(
            TaskCommandRepository taskCommandRepository,
            TransactionManager transactionManager,
            IDGenerator taskIdGenerator
    ) {
        return new CreateTaskHandler(taskCommandRepository, transactionManager, taskIdGenerator);
    }

    @Bean
    public UpdateTaskHandler updateTaskHandler(TaskCommandRepository taskCommandRepository) {
        return new UpdateTaskHandler(taskCommandRepository);
    }

    @Bean
    public GetTaskBySidHandler getTaskBySidHandler(TaskQueryRepository taskQueryRepository) {
        return new GetTaskBySidHandler(taskQueryRepository);
    }

    @Bean
    public ListTasksHandler listTasksHandler(TaskQueryRepository taskQueryRepository) {
        return new ListTasksHandler(taskQueryRepository);
    }


}
