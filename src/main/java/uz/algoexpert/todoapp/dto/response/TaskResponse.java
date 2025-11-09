package uz.algoexpert.todoapp.dto.response;

import lombok.Data;
import uz.algoexpert.todoapp.model.Task;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    public TaskResponse toTaskResponse() {
        return Task.getTaskResponse(this.id, this.title, this.description, this.completed, this.createdAt, this.dueDate);
    }
}
