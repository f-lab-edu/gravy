package kr.gravy.gravy.email.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

public class SendEmailVerificationCodeDto {

    @Getter
    public static class Request {
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
    }
}
