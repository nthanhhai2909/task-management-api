-- Enable pgcrypto for gen_random_uuid
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =============================================
-- Task ID sequence
-- =============================================
CREATE SEQUENCE IF NOT EXISTS task_id_seq START WITH 1 INCREMENT BY 1;

-- =============================================
-- Task entity table
-- =============================================
CREATE TABLE IF NOT EXISTS tasks (
                       id          BIGINT        PRIMARY KEY,
                       sid         VARCHAR(36)   NOT NULL,
                       title       VARCHAR(255)  NOT NULL,
                       description TEXT,
                       created_by  VARCHAR(100)  NOT NULL,
                       created_at  TIMESTAMP     NOT NULL DEFAULT now(),
                       assignee    VARCHAR(100),
                       priority    VARCHAR(20)   NOT NULL DEFAULT 'MEDIUM',
                       status      VARCHAR(20)   NOT NULL DEFAULT 'TODO',
                       due_date    TIMESTAMP,
                       version     BIGINT        NOT NULL DEFAULT 0,
                       deleted     BOOLEAN       NOT NULL DEFAULT FALSE,

                       CONSTRAINT uq_tasks_sid UNIQUE (sid)
);

-- =============================================
-- Task domain events table (outbox pattern)
-- =============================================
CREATE SEQUENCE IF NOT EXISTS task_event_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS task_events (
                             id             BIGINT        PRIMARY KEY DEFAULT nextval('task_event_id_seq'),
                             sid            VARCHAR(36)   NOT NULL,
                             event_type     VARCHAR(100)  NOT NULL,
                             aggregate_id   BIGINT        NOT NULL,
                             aggregate_sid  VARCHAR(36)   NOT NULL,
                             payload        JSONB         NOT NULL,
                             occurred_on    TIMESTAMP     NOT NULL,
                             published      BOOLEAN       NOT NULL DEFAULT FALSE,
                             created_at     TIMESTAMP     NOT NULL DEFAULT now(),

                             CONSTRAINT uq_task_events_sid UNIQUE (sid),
                             CONSTRAINT fk_task_events_task FOREIGN KEY (aggregate_id) REFERENCES tasks (id)
);
