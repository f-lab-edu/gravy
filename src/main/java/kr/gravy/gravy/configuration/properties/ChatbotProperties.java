package kr.gravy.gravy.configuration.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "chatbot")
public record ChatbotProperties(
        @NotBlank String pythonServerUrl,
        Integer timeoutSeconds
) {
    public ChatbotProperties {
        if (timeoutSeconds == null) {
            timeoutSeconds = 30;
        }
    }
}