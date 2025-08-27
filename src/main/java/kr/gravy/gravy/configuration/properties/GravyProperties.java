package kr.gravy.gravy.configuration.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "gravy")
public record GravyProperties(
        @NotBlank String baseUrl,
        @Valid @NotNull Security security
) {

    public record Security(@Valid @NotNull Cookie cookie) {
        public record Cookie(boolean secure) {
        }
    }
}
