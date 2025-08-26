package kr.gravy.gravy.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * 애플리케이션의 기본 URL
 * 예: "http://localhost:8080", "https://gravy.kr"
 */

@Validated
@ConfigurationProperties(prefix = "app")
public record AppProperties(@NotBlank String baseUrl) {
}
