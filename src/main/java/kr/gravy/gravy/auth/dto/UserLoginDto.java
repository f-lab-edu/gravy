package kr.gravy.gravy.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {

    public record Request(@Email String email,
                          @NotBlank String password) {
    }

    public record Response(String accessToken, String refreshToken) {
    }

}
