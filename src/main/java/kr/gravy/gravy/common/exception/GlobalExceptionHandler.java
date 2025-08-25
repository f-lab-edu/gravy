package kr.gravy.gravy.common.exception;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static kr.gravy.gravy.common.exception.Status.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${app.base-url}")
    private String baseUrl;

    @PostConstruct
    void init() {
        initFallbackUri(baseUrl);
        for (Status status : values()) {
            status.initDocUri(baseUrl);
        }
    }

    @ExceptionHandler(GravyException.class)
    public ResponseEntity<ProblemDetail> handleGravyException(GravyException gravyException, HttpServletRequest request) {
        log.error("GravyException: {}", gravyException.getMessage(), gravyException);

        Status status = gravyException.getStatus();
        ProblemDetail problemDetail = status.toProblemDetail(URI.create(request.getRequestURI()));

        return ResponseEntity
                .status(status.getHttpStatusCode())
                .body(problemDetail);
    }

    // Validation 예외 처리 - @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("ValidException: {}", e.getMessage(), e);

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ProblemDetail problemDetail = VALIDATION_FAILED.toProblemDetail(URI.create(request.getRequestURI()));
        problemDetail.setProperty("invalidFields", errors);

        return ResponseEntity.badRequest().body(problemDetail);
    }
}
