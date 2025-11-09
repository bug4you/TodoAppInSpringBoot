package uz.algoexpert.todoapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.algoexpert.todoapp.dto.response.ApiResponse;
import uz.algoexpert.todoapp.model.ActionType;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleTaskNotFoundException(TaskNotFoundException ex) {
        return ApiResponse.getResponse(
                false,
                ActionType.TASK_NOT_FOUND,
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        return ApiResponse.getResponse(
                false,
                ActionType.BACKEND_SERVICE_ERROR,
                "An unexpected error occurred: " + ex.getMessage(),
                null
        );
    }

//    NOT VALID EXCEPTION
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse(ex.getMessage());

        return ApiResponse.getResponse(
                false,
                ActionType.BAD_REQUEST,
                errorMessage,
                null
        );
    }
}
