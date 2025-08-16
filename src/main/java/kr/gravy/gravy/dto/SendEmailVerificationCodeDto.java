package kr.gravy.gravy.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

public class SendEmailVerificationCodeDto {

    @Getter
    public static class Request {
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
    }
}
