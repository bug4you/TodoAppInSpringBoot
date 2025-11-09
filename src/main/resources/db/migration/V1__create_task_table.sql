-- src/main/resources/db/migration/V1__create_task_table.sql
CREATE TABLE tasks (
                       id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,  -- ⬅️ DEFAULT avval, keyin PRIMARY KEY
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       completed BOOLEAN DEFAULT FALSE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       due_date TIMESTAMP
);

CREATE INDEX idx_tasks_completed ON tasks(completed);