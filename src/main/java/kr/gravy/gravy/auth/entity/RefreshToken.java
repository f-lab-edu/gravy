package kr.gravy.gravy.auth.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    private Long id;
    private Long userId;
    private String token;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private RefreshToken(Long userId, String token, LocalDateTime expiredAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.token = token;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static RefreshToken create(Long userId, String token, LocalDateTime expiredAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new RefreshToken(userId, token, expiredAt, createdAt, updatedAt);
    }
}
