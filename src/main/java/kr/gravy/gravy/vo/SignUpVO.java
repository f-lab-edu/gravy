package kr.gravy.gravy.vo;

import kr.gravy.gravy.enumeration.EmailVerificationStatus;
import lombok.Getter;

@Getter
public class SignUpVO {
    private Long id;
    private String email;
    private EmailVerificationStatus status;
}
