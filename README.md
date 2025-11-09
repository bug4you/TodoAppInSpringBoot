# TodoApp – Spring Boot + Flyway + REST API

> **Zamonaviy TODO ilovasi** – Spring Boot 3, JPA, Flyway migratsiyalari va RESTful API asosida qurilgan.  
> Professional arxitektura: **Entity → DTO → Service → Controller → Exception Handling**.

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-6DB33F?style=for-the-badge&logo=springboot)
![H2](https://img.shields.io/badge/H2-Database-blue?style=for-the-badge&logo=h2)
![Flyway](https://img.shields.io/badge/Flyway-Migration-red?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven)

---

## Loyiha haqida

- **Backend**: Spring Boot 3.5.7 (Java 25)
- **DB**: H2 (development), PostgreSQL (production)
- **Migratsiya**: Flyway (versiyalangan SQL)
- **Arxitektura**: Layered (Model, DTO, Repository, Service, Controller)
- **API**: RESTful JSON
- **Validatsiya**: Hibernate Validator
- **Test**: JUnit 5 + Spring Boot Test

---

## Loyiha tuzilmasi

```bash
src/main/java/uz/algoexpert/todoapp/
├── config
│   └── WebConfig.java
├── controller
│   └── TaskController.java
├── dto
│   ├── request
│   │   └── CreateTaskRequest.java
│   └── response
│       ├── ApiResponse.java
│       └── TaskResponse.java
├── exception
│   ├── GlobalExceptionHandler.java
│   └── TaskNotFoundException.java
├── model
│   ├── ActionType.java
│   └── Task.java
├── repository
│   └── TaskRepository.java
├── service
│   └── TaskService.java
└── TodoAppApplication.java

src/main/resources/
├── application.yml
├── db
│   └── migration
│       ├── V1__create_task_table.sql
│       ├── V2__add_due_date.sql
│       └── V3__insert_sample_data.sql
├── static
└── templates
```

---

## DB Migratsiyalari (Flyway)

| Versiya | Fayl nomi                | Maqsad                       |
|---------|--------------------------|------------------------------|
| V1      | `create_task_table.sql`  | `tasks` jadvalini yaratish   |
| V2      | `add_due_date.sql`       | `due_date` ustunini qo‘shish |
| V3      | `insert_sample_data.sql` | Namuna ma’lumotlar kiritish  |

> Flyway avtomatik ishlaydi. `flyway_schema_history` jadvali barcha migratsiyalarni kuzatadi.

---

## API Endpointlar

| Method   | URL                      | Tavsif                        | Request Body |
|----------|--------------------------|-------------------------------|--------------|
| `GET`    | `/api/tasks`             | Barcha vazifalar              | –            |
| `GET`    | `/api/tasks/active`      | Faol vazifalar                | –            |
| `GET`    | `/api/tasks/completed`   | Tugallangan vazifalar         | –            |
| `POST`   | `/api/tasks`             | Yangi vazifa qo‘shish         | JSON         |
| `PATCH`  | `/api/tasks/{id}/toggle` | Holatni o‘zgartirish (toggle) | –            |
| `DELETE` | `/api/tasks/{id}`        | Vazifani o‘chirish            | –            |

### POST misoli (curl)

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
  "title": "Finish the report",
  "description": "Complete the quarterly financial report by end of the week.",
  "dueDate": "2025-12-31T23:59:59"
}'
```

---

## Ishga tushirish

### 1. Maven orqali

```bash
./mvnw spring-boot:run
```

> Ilova `http://localhost:8080` da ishlaydi.

### 2. H2 konsolni ochish

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:todo_db`
- Username: `sa`, 
- Password: `bo‘sh`

---

## Test qilish

```bash
./mvnw test
```

> `@DataJpaTest` bilan Flyway migratsiyalari avtomatik ishlaydi.

---

## Productionga o‘tish (PostgreSQL)

### 1. `application-prod.yml` yarating

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/todo_prod
    username: todo_user
    password: ${DB_PASSWORD}
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: none
  flyway:
    enabled: true
    locations: classpath:db/migration
```

### 2. PostgreSQL driverini `pom.xml` ga qo‘shing

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 3. Profilni faollashtiring

```bash
SPRING_PROFILES_ACTIVE=prod ./mvnw spring-boot:run
```

---

## Docker bilan deploy (ixtiyoriy)

### `docker-compose.yml`

```yaml
version: '3.8'
services:
  db:
    image: postgres:15
    environment:
      POSTGRES_DB: todo_prod
      POSTGRES_USER: todo_user
      POSTGRES_PASSWORD: secret123
    ports:
      - "5432:5432"

  app:
    build: .
    depends_on:
      - db
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_PASSWORD=secret123
    ports:
      - "8080:8080"
```

```bash
docker-compose up --build
```

---

## Keyingi qadamlar (tavsiyalar)

| # | Vazifa                              | Texnologiya               |
|---|-------------------------------------|---------------------------|
| 1 | Mobil frontend                      | **Flutter (Dart)**        |
| 2 | Autentifikatsiya                    | **Spring Security + JWT** |
| 3 | Swagger UI                          | `springdoc-openapi`       |
| 4 | CI/CD                               | GitHub Actions            |

---

## Muallif

- **Dasturchi**: Dilshod Fayzullayev
- **Loyiha**: `uz.algoexpert.todoapp`
- **GitHub**: [github.com/https://github.com/bug4you/TodoAppInSpringBoot](https://github.com)

---

## Litsenziya

```
MIT License – erkin foydalanish mumkin.
```