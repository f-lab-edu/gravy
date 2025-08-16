package kr.gravy.gravy.mapper;

import kr.gravy.gravy.enumeration.EmailVerificationStatus;
import kr.gravy.gravy.vo.EmailVerificationVO;
import kr.gravy.gravy.vo.SendEmailVerificationCodeVO;
import kr.gravy.gravy.vo.SignUpVO;
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
