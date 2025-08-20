package kr.gravy.gravy.email.dto;

import jakarta.validation.constraints.Email;

public class SendEmailVerificationCodeDto {

    public record Request(@Email(message = "이메일 형식이 아닙니다.") String email) {
    }
}
