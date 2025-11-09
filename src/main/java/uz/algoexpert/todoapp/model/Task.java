package uz.algoexpert.todoapp.model;

import jakarta.persistence.*;
import lombok.*;
import uz.algoexpert.todoapp.dto.response.TaskResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    private String title;

    private String description;

    private boolean completed;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public TaskResponse toTaskResponse() {
        return getTaskResponse(this.id, this.title, this.description, this.completed, this.createdAt, this.dueDate);
    }

    public static TaskResponse getTaskResponse(UUID id, String title, String description, boolean completed, LocalDateTime createdAt, LocalDateTime dueDate) {
        TaskResponse response = new TaskResponse();
        response.setId(id);
        response.setTitle(title);
        response.setDescription(description);
        response.setCompleted(completed);
        response.setCreatedAt(createdAt);
        response.setDueDate(dueDate);
        return response;
    }
}
