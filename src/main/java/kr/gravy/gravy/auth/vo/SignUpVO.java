package kr.gravy.gravy.auth.vo;

import kr.gravy.gravy.email.model.EmailVerificationStatus;
import lombok.Getter;

@Getter
public class SignUpVO {
    private Long id;
    private String email;
    private EmailVerificationStatus status;
}
