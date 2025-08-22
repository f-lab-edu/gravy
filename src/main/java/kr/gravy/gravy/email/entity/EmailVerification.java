package kr.gravy.gravy.email.entity;

import kr.gravy.gravy.email.model.EmailVerificationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailVerification {
    private Long id;
    private UUID publicId;
    private String verificationCode;
    private EmailVerificationStatus status;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EmailVerification(UUID publicId, String verificationCode, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.publicId = publicId;
        this.verificationCode = verificationCode;
        this.status = EmailVerificationStatus.SENT;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EmailVerification create(UUID publicId, String verificationCode, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new EmailVerification(publicId, verificationCode, email, createdAt, updatedAt);
    }
}
