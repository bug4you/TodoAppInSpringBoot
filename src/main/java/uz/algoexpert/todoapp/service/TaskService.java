package uz.algoexpert.todoapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.algoexpert.todoapp.dto.request.CreateTaskRequest;
import uz.algoexpert.todoapp.dto.response.ApiResponse;
import uz.algoexpert.todoapp.dto.response.TaskResponse;
import uz.algoexpert.todoapp.exception.TaskNotFoundException;
import uz.algoexpert.todoapp.model.ActionType;
import uz.algoexpert.todoapp.model.Task;
import uz.algoexpert.todoapp.repository.TaskRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * * Retrieves all tasks from the repository.
     *
     * @param page Page number for pagination.
     * @param size Number of tasks per page.
     * @return ApiResponse containing a set of TaskResponse objects.
     */
    public ApiResponse<Set<TaskResponse>> getAllTasks(int page, int size) {
        log.info("Fetching all tasks with pagination - page: {}, size: {}", page, size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Set<TaskResponse> taskResponses = taskRepository.findAll(pageRequest)
                .stream().map(
                        task -> {
                            TaskResponse response = new TaskResponse();
                            response.setId(task.getId());
                            response.setTitle(task.getTitle());
                            response.setDescription(task.getDescription());
                            response.setCompleted(task.isCompleted());
                            response.setCreatedAt(task.getCreatedAt());
                            response.setDueDate(task.getDueDate() != null ? task.getDueDate() : null);
                            return response;
                        }
                ).collect(Collectors.toSet());
        return new ApiResponse<>(
                true,
                ActionType.ALL_TASKS_RETRIEVED,
                "All tasks retrieved successfully",
                taskResponses
        );
    }

    /**
     * Retrieves tasks based on their completion status.
     *
     * @param completed Boolean indicating the completion status to filter tasks.
     * @return List of Task entities matching the specified completion status.
     */
    public ApiResponse<List<Task>> findAllByCompleted(Boolean completed) {
        log.info("Fetching tasks with completed status: {}", completed);
        return new ApiResponse<>(
                true,
                completed ? ActionType.ALL_COMPLETED_TASKS_RETRIEVED : ActionType.ALL_INCOMPLETE_TASKS_RETRIEVED,
                completed ? "Tasks retrieved successfully" : "Incomplete tasks retrieved successfully",
                taskRepository.findByCompleted(completed)
        );
    }

    /**
     * Creates a new task based on the provided request data.
     *
     * @param taskRequest CreateTaskRequest object containing task details.
     * @return ApiResponse containing the created TaskResponse object.
     */
    public ApiResponse<TaskResponse> createTask(CreateTaskRequest taskRequest) {
        log.info("Creating a new task with title: {}", taskRequest.getTitle());
        Task task = taskRequest.toTask();
        Task savedTask = taskRepository.save(task);
        return new ApiResponse<>(
                true,
                ActionType.TASK_CREATED,
                "Task created successfully",
                savedTask.toTaskResponse()
        );
    }

    public ApiResponse<TaskResponse> toggleComplete(UUID id) {
        log.info("Toggling completion status for task with ID: {}", id);
        return taskRepository.findById(id)
                .map(task -> {
                    task.setCompleted(!task.isCompleted());
                    Task updatedTask = taskRepository.save(task);
                    return new ApiResponse<>(
                            true,
                            task.isCompleted() ? ActionType.TASK_COMPLETED : ActionType.TASK_REOPENED,
                            task.isCompleted() ? "Task marked as completed" : "Task reopened",
                            updatedTask.toTaskResponse()
                    );
                })
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
    }

    /**
     * Deletes all tasks from the repository.
     *
     * @return ApiResponse indicating the result of the deletion operation.
     */
    public ApiResponse<?> deleteAllTasks() {
        log.info("Deleting all tasks from the repository");
        taskRepository.deleteAll();
        return new ApiResponse<>(
                true,
                ActionType.ALL_TASKS_DELETED,
                "All tasks have been deleted successfully",
                null
        );
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id UUID of the task to be retrieved.
     * @return ApiResponse containing the TaskResponse if found, or an error message if not found.
     * */
    public ApiResponse<TaskResponse> getTaskById(UUID id) {
        log.info("Fetching task with ID: {}", id);
        return taskRepository.findById(id)
                .map(task -> new ApiResponse<>(
                        true,
                        ActionType.TASK_CREATED,
                        "Task retrieved successfully",
                        task.toTaskResponse()
                ))
                .orElseGet(() -> new ApiResponse<>(
                        false,
                        ActionType.TASK_NOT_FOUND,
                        "Task not found",
                        null
                ));
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id UUID of the task to be deleted.
     * @return ApiResponse indicating the result of the deletion operation.
     * */
    public ApiResponse<?> deleteTaskById(UUID id) {
        log.info("Deleting task with ID: {}", id);
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return new ApiResponse<>(
                    true,
                    ActionType.TASK_DELETED,
                    "Task deleted successfully",
                    null
            );
        }
        throw new TaskNotFoundException("Task with ID " + id + " not found");
    }
}
