package kr.gravy.gravy.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GravyException.class)
    public ResponseEntity<ErrorResponse> handleGravyException(GravyException e) {
        log.error("GravyException: {}", e.getMessage(), e);

        ErrorResponse response = new ErrorResponse(
                e.getCode(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(e.getHttpStatusCode())
                .body(response);
    }


    // Validation 예외 처리 - @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse response = new ErrorResponse(
                "VALID001",
                "입력값 검증에 실패했습니다: " + errors,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(response);
    }


    // 에러 응답 DTO
    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private String code;
        private String message;
        private LocalDateTime timestamp;
    }
}
