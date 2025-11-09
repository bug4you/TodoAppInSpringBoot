package uz.algoexpert.todoapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.algoexpert.todoapp.dto.request.CreateTaskRequest;
import uz.algoexpert.todoapp.dto.response.ApiResponse;
import uz.algoexpert.todoapp.dto.response.TaskResponse;
import uz.algoexpert.todoapp.model.Task;
import uz.algoexpert.todoapp.service.TaskService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = {"/api/tasks", "/api/tasks/"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponse<Set<TaskResponse>>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ok(taskService.getAllTasks(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getById(@PathVariable UUID id) {
        return ok(taskService.getTaskById(id));
    }

    @GetMapping("/isCompleted={status}")
    public ResponseEntity<ApiResponse<List<Task>>> getActive(@PathVariable boolean status) {
        return ResponseEntity.ok(taskService.findAllByCompleted(status));
    }

    @GetMapping("/completed")
    public ResponseEntity<ApiResponse<List<Task>>> getCompleted() {
        return ResponseEntity.ok(taskService.findAllByCompleted(true));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody CreateTaskRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(error -> {
                        if ("dueDate".equals(error.getField())) {
                            return "Due date must be in ISO 8601 format, e.g. '2025-12-31T23:59:59'";
                        }
                        return error.getDefaultMessage();
                    })
                    .findFirst()
                    .orElse("Validation failed");

            return ApiResponse.getErrorResponse(errorMessage);
        }
        return ResponseEntity.ok(taskService.createTask(request));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<TaskResponse>> toggle(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.toggleComplete(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteAll() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(taskService.deleteAllTasks());
    }
}
