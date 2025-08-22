package kr.gravy.gravy.auth.entity;

import kr.gravy.gravy.auth.model.Grade;
import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class User {
    private Long id;
    private UUID publicId;
    private String nickname;
    private String email;
    private String password;
    private Grade grade;
    private LocalDateTime createdAt;

    public void validatePassword(PasswordEncoder passwordEncoder, String requestedPassword) {
        if (!passwordEncoder.matches(requestedPassword, password)) {
            throw new GravyException(Status.AUTHENTICATION_FAILED);
        }
    }
}
