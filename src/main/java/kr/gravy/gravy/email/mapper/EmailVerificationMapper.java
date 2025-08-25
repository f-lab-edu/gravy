package kr.gravy.gravy.email.mapper;

import kr.gravy.gravy.email.entity.EmailVerification;
import kr.gravy.gravy.email.model.EmailVerificationStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationMapper {

    void insertVerificationCode(EmailVerification emailVerification);

    Optional<EmailVerification> getLatestVerification(@Param("email") String email);

    void updateVerificationStatus(@Param("emailVerificationId") Long emailVerificationId,
                                  @Param("emailVerificationStatus") EmailVerificationStatus emailVerificationStatus);

    Optional<EmailVerification> getEmailVerificationByPublicId(@Param("emailVerificationPublicId") UUID emailVerificationPublicId);
}
