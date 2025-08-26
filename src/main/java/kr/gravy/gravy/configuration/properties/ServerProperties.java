package kr.gravy.gravy.configuration.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * SSL 활성화 여부
 * 로컬 개발환경에서는 false, 운영환경에서는 true
 */
@Validated
@ConfigurationProperties(prefix = "server")
public record ServerProperties(@NotNull Ssl ssl) {
    public record Ssl(boolean enabled) {
    }
}
