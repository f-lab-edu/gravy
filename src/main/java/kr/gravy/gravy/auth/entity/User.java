package kr.gravy.gravy.auth.entity;

import kr.gravy.gravy.auth.model.Grade;
import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Long id;
    private UUID publicId;
    private String nickname;
    private String email;
    private String password;
    private Grade grade;
    private LocalDateTime createdAt;

    private User(UUID publicId, String nickname, String email, String password, Grade grade, LocalDateTime createdAt) {
        this.publicId = publicId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.grade = grade;
        this.createdAt = createdAt;
    }

    public static User createForSignUp(UUID publicId, String nickname, String email, String password, Grade grade, LocalDateTime createdAt) {
        return new User(publicId, nickname, email, password, grade, createdAt);
    }

    public void validatePassword(PasswordEncoder passwordEncoder, String requestedPassword) {
        if (!passwordEncoder.matches(requestedPassword, password)) {
            throw new GravyException(Status.AUTHENTICATION_FAILED);
        }
    }
}
