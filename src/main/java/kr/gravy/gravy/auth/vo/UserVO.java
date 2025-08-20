package kr.gravy.gravy.auth.vo;

import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import kr.gravy.gravy.auth.enumeration.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
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
