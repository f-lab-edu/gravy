package kr.gravy.gravy.email.mapper;

import kr.gravy.gravy.auth.vo.SignUpVO;
import kr.gravy.gravy.email.model.EmailVerificationStatus;
import kr.gravy.gravy.email.vo.EmailVerificationVO;
import kr.gravy.gravy.email.vo.SendEmailVerificationCodeVO;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationMapper {

    void insertVerificationCode(SendEmailVerificationCodeVO sendEmailVerificationCodeVO);

    Optional<EmailVerificationVO> getLatestVerification(@Param("email") String email);

    void updateVerificationStatus(@Param("emailVerificationId") Long emailVerificationId,
                                  @Param("emailVerificationStatus") EmailVerificationStatus emailVerificationStatus);

    Optional<SignUpVO> getEmailAndVerificationStatusCode(@Param("emailVerificationPublicId") UUID emailVerificationPublicId);
}
