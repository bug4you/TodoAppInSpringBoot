package uz.algoexpert.todoapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import uz.algoexpert.todoapp.model.Task;

import java.time.LocalDateTime;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dueDate; // ISO 8601 format

    public Task toTask() {
        return Task.builder()
                .title(this.title)
                .description(this.description)
                .completed(false)
                .dueDate(this.dueDate)
                .build();
    }
}
