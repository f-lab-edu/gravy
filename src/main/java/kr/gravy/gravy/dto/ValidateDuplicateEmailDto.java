package kr.gravy.gravy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ValidateDuplicateEmailDto {

    @Getter
    public static class Request {

        @NotBlank
        private String email;
    }

}
