package kr.gravy.gravy.auth.dto;

import lombok.Builder;
import lombok.Getter;

public class ReIssueAccessTokenDto {

    @Getter
    @Builder
    public static class Response {

        private String accessToken;

        private String refreshToken;

    }

}
