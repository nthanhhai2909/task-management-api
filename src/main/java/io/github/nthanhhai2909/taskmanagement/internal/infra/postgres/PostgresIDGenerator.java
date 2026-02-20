package io.github.nthanhhai2909.taskmanagement.internal.infra.postgres;

import io.github.nthanhhai2909.taskmanagement.internal.application.db.GeneratedId;
import io.github.nthanhhai2909.taskmanagement.internal.application.db.IDGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class PostgresIDGenerator implements IDGenerator {

    private final JdbcTemplate jdbcTemplate;
    private final String query;

    public PostgresIDGenerator(JdbcTemplate jdbcTemplate, String sequenceName) {
        this.jdbcTemplate = jdbcTemplate;
        Assert.hasText(sequenceName, "sequenceName must not be empty");
        this.query = String.format("SELECT nextval('%s') AS id, gen_random_uuid()::text AS sid", sequenceName);
    }

    @Override
    public GeneratedId get() {
        RowMapper<GeneratedId> mapper = (rs, rowNum) -> {
            long id = rs.getLong("id");
            String sid = rs.getString("sid");
            return new GeneratedId(id, sid);
        };

        GeneratedId gid = jdbcTemplate.queryForObject(query, mapper);
        return gid;
    }
}
