package kr.gravy.gravy.vo;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class EmailVerificationVO {
    private Long id;
    private UUID publicId;
    private String verificationCode;
    private LocalDateTime createdAt;
}
