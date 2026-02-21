````markdown name=requirements.md
# Task Management Project — Requirements

## 1. Overview
Build a **Task Management** system with:
- A **Backend REST API** (Spring Boot + Java + Gradle)
- A separate **Frontend application** (optional, separate repo) consuming the API

Primary goal: practice building a real-world CRUD application with clean API design, database migrations, filtering, pagination, and (optionally) authentication.

---

## 2. Features (what the project should have)

### 2.1 Task lifecycle (Core / must-have)
- Create a task
- View task details
- Edit task (title/description/due date/priority)
- Update task status:
  - `TODO` → `IN_PROGRESS` → `DONE`
  - also support `CANCELLED`
- Delete a task (either **hard delete** or **archive/soft delete** — choose one approach)

### 2.2 Task organization (Should-have)
- Task priority: `LOW`, `MEDIUM`, `HIGH`
- Due date (optional)
- Overdue indicator (computed in frontend or backend response)
- Tags/Labels (optional, can be Phase 2)
- Projects/Lists to group tasks (optional, can be Phase 2)
- Subtasks/checklist (optional, can be Phase 3)

### 2.3 Listing, search, filters (Frontend-ready / must-have)
- List tasks with:
  - Pagination (`page`, `size`)
  - Sorting (`sort=createdAt,desc`, etc.)
  - Filters:
    - by `status`
    - by `priority`
    - by due date range (`dueAfter`, `dueBefore`)
  - Search keyword `q` (search title/description)

### 2.4 Quality rules (must-have)
- Validation rules:
  - `title` is required and cannot be blank
  - enforce max length for fields (e.g., title <= 200, description <= 2000)
  - `dueDate` must be a valid ISO date-time if provided
- Consistent API error response format:
  - include message + field errors for validation failures
- Use consistent HTTP status codes:
  - `201` create, `200` read/update, `204` delete, `400` validation errors, `404` not found

### 2.5 Documentation (should-have)
- Provide OpenAPI/Swagger documentation for all endpoints
- Include example requests/responses

### 2.6 Authentication & users (Optional / Phase-based)
Phase 1 (simpler): **no auth**, single-user mode.

Phase 2 (advanced): add:
- Register/login
- JWT-based authentication
- Authorization so each user can only access their own tasks

### 2.7 Collaboration (Optional / advanced)
- Assign task to user
- Comments on tasks
- Activity log (audit trail)

### 2.8 Notifications & reminders (Optional / advanced)
- Reminder for due tasks (email or in-app)
- Overdue notification

---

## 3. Data model (minimum)

### 3.1 Task (required)
**Fields**
- `id` (UUID or Long)
- `title` (string, required)
- `description` (string, optional)
- `status` (enum: `TODO`, `IN_PROGRESS`, `DONE`, `CANCELLED`)
- `priority` (enum: `LOW`, `MEDIUM`, `HIGH`)
- `dueDate` (date-time, optional)
- `createdAt` (server-generated)
- `updatedAt` (server-generated)

**Defaults**
- `status` defaults to `TODO`
- `priority` defaults to `MEDIUM` (or null — choose one)

---

## 4. Backend API requirements (REST)

Base path: `/api/v1`

### 4.1 Health
- `GET /health` — returns service status and timestamp

### 4.2 Tasks
- `POST /tasks` — create task
- `GET /tasks/{id}` — get task details
- `PUT /tasks/{id}` — full update/replace task
- `PATCH /tasks/{id}` — partial update (status/title/etc.)
- `DELETE /tasks/{id}` — delete/archive task
- `GET /tasks` — list tasks with pagination/filter/search/sort

**List endpoint query parameters**
- `page` (default `0`)
- `size` (default `20`)
- `sort` (e.g. `createdAt,desc`)
- `status` (optional)
- `priority` (optional)
- `q` (optional search)
- `dueAfter`, `dueBefore` (optional)

---

## 5. Database & migrations
- Use PostgreSQL
- Use Flyway migrations:
  - `V1__create_tasks_table.sql`
  - future versions for tags/projects/users if needed
- Add indexes for common queries (recommended):
  - `status`, `priority`, `due_date`, `created_at`
  - and `owner_id` if auth is added

---

## 6. Non-functional requirements
- Java 17, Spring Boot, Gradle wrapper
- Logging enabled (structured logs recommended)
- CORS configured so the frontend can call the API
- Testing:
  - unit tests for service/domain logic
  - integration tests for controller/repository (recommended)

---

## 7. Suggested phases (implementation plan)

### Phase 1 (MVP / best for practice)
- Tasks CRUD
- Pagination + filters + search
- Validation + consistent error responses
- PostgreSQL + Flyway + docker-compose
- Swagger/OpenAPI docs

### Phase 2 (Product-like)
- Tags and/or Projects
- Archive instead of hard delete (if not done)
- Better search + sorting
- Frontend application

### Phase 3 (Advanced)
- Authentication + multi-user
- Comments + activity log
- Notifications/reminders
- Subtasks/dependencies

---

## 8. Frontend (separate repo) — minimum requirements
- Pages:
  - Task list
  - Create/Edit task form
  - Task detail view
- Capabilities:
  - search, filters, sorting, pagination
  - status change (quick action)
  - handle API errors gracefully
- Config:
  - API base URL via environment variable
````
