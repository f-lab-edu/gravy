package kr.gravy.gravy.email.entity;

import kr.gravy.gravy.email.model.EmailVerificationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class EmailVerification {
    private Long id;
    private UUID publicId;
    private String verificationCode;
    private EmailVerificationStatus status;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
