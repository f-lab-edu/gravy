package kr.gravy.gravy.email.vo;

import kr.gravy.gravy.email.enumeration.EmailVerificationStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SendEmailVerificationCodeVO {
    private String verificationCode;
    private UUID publicId;
    private EmailVerificationStatus status;
    private String email;
}
