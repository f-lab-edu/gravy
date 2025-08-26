package kr.gravy.gravy.configuration.properties;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * CORS에서 허용할 Origin 목록
 * 예: ["http://localhost:5173", "https://gravy.kr"]
 */
@Validated
@ConfigurationProperties(prefix = "cors")
public record CorsProperties(@NotEmpty List<String> allowedOrigins) {
}
