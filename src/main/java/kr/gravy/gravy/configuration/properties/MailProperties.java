package kr.gravy.gravy.configuration.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "spring.mail")
public record MailProperties(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String host,
        @Min(587) @Max(587) int port,
        @Valid Transport transport,
        @Valid Smtp smtp
) {


    public record Transport(@NotBlank String protocol) {
    }

    public record Smtp(
            boolean auth,
            @Valid @NotNull
            Starttls starttls,

            @NotNull
            Duration connectiontimeout,

            @NotNull
            Duration timeout,

            @NotNull
            Duration writetimeout
    ) {
    }

    public record Starttls(boolean enable) {
    }

}
