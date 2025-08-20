package kr.gravy.gravy.auth.dto;

public class ReIssueAccessTokenDto {

    public record Response(String accessToken, String refreshToken) {
    }
}
