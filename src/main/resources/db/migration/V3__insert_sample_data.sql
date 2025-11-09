-- src/main/resources/db/migration/V3__insert_sample_data.sql

INSERT INTO tasks (title, description, completed, due_date) VALUES
                                                                ('Spring Boot o''rganish', 'Flyway bilan DB migratsiyasi', FALSE, '2025-12-01'),
                                                                ('Flutter ilova qilish', 'Dart tilida TODO frontend', FALSE, '2025-12-15'),
                                                                ('Docker deploy', 'PostgreSQL + Spring Boot', FALSE, '2025-12-20');