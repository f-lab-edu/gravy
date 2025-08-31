package kr.gravy.gravy.auth.dto;

public class ReissueAccessTokenDto {

    public record Response(String accessToken, String refreshToken) {
    }
}
