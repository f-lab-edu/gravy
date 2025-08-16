package kr.gravy.gravy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class VerifyEmailVerificationCodeDto {

    @Getter
    public static class Request {

        @Email
        private String email;

        @NotBlank
        private String verificationCode;
    }

    @Getter
    @Builder
    public static class Response {

        private UUID verificationPublicId;
    }
}
