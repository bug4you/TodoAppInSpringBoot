-- src/main/resources/db/migration/V3__insert_sample_data.sql
INSERT INTO tasks (id, title, description, completed, created_at, due_date) VALUES
                                                                                (RANDOM_UUID(), 'Spring Boot o''rganish', 'Flyway bilan DB migratsiyasi', FALSE, CURRENT_TIMESTAMP, TIMESTAMP '2025-12-01 23:59:59'),
                                                                                (RANDOM_UUID(), 'Flutter ilova qilish', 'Dart tilida TODO frontend', FALSE, CURRENT_TIMESTAMP, TIMESTAMP '2025-12-15 23:59:59'),
                                                                                (RANDOM_UUID(), 'Docker deploy', 'PostgreSQL + Spring Boot', FALSE, CURRENT_TIMESTAMP, TIMESTAMP '2025-12-20 23:59:59');