package kr.gravy.gravy.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class UserLoginDto {

    @Getter
    public static class Request {

        @Email
        private String email;

        @NotBlank
        private String password;
    }


    @Getter
    @Builder
    public static class Response {

        private String accessToken;

        private String refreshToken;
    }

}
