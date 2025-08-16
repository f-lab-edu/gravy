package kr.gravy.gravy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserSignUpDto {

    @Getter
    public static class Request {

        @NotBlank(message = "닉네임은 필수값입니다.")
        private String nickname;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;

        @NotNull
        private UUID verificationPublicId;
    }
}
