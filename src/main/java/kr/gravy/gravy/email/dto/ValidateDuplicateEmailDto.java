package kr.gravy.gravy.email.dto;

import jakarta.validation.constraints.NotBlank;

public class ValidateDuplicateEmailDto {

    public record Request(@NotBlank String email) {
    }

}
