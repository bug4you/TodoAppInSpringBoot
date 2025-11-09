package uz.algoexpert.todoapp.dto.response;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.algoexpert.todoapp.model.ActionType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiResponse<T> {
    private boolean success;
    private ActionType actionType;
    private String message;
    private T data = null;

    public static <T> ResponseEntity<ApiResponse<?>> getSuccessResponse(ActionType actionType, String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .actionType(actionType)
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<?>> getErrorResponse(String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    public static <T> ResponseEntity<ApiResponse<?>> getResponse(boolean isSuccess, ActionType actionType, String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(isSuccess)
                .actionType(actionType)
                .message(message)
                .data(data)
                .build();
        if (isSuccess) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
