package kr.gravy.gravy.email.service;

import kr.gravy.gravy.auth.mapper.UserMapper;
import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import kr.gravy.gravy.common.utils.GeneratorUtil;
import kr.gravy.gravy.email.configuration.MailConfiguration;
import kr.gravy.gravy.email.dto.SendEmailVerificationCodeDto;
import kr.gravy.gravy.email.dto.VerifyEmailVerificationCodeDto;
import kr.gravy.gravy.email.entity.EmailVerification;
import kr.gravy.gravy.email.mapper.EmailVerificationMapper;
import kr.gravy.gravy.email.model.EmailVerificationStatus;
import kr.gravy.gravy.email.util.VerificationCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final JavaMailSender javaMailSender;
    private final EmailVerificationMapper emailVerificationMapper;
    private final UserMapper userMapper;

    @Transactional
    public void sendEmailVerificationCode(final SendEmailVerificationCodeDto.Request request) {
        final String verificationCode = VerificationCodeGenerator.generateVerificationCode();
        final LocalDateTime now = LocalDateTime.now();

        EmailVerification emailVerification = EmailVerification.create(
                GeneratorUtil.generatePublicId(),
                verificationCode,
                request.email(),
                now,
                now
        );

        emailVerificationMapper.insertVerificationCode(emailVerification);

        // TODO:: 하루에 메일 당 최대 10번 요청 가능, 한번 요청 후 30초 후 요청 가능
        sendEmail(request.email(), verificationCode);
    }

    private void sendEmail(final String userEmail, final String verificationCode) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(userEmail);
            mail.setSubject("Gravy 인증 코드");
            mail.setText(verificationCode);
            javaMailSender.send(mail);
        } catch (MailException e) {
            log.error("이메일 발송 실패. 수신자: {}, 원인: {}", userEmail, e.getMessage(), e);
            throw new GravyException(Status.FAIL_SEND_MAIL);
        }
    }


    @Transactional
    public VerifyEmailVerificationCodeDto.Response verifyEmailVerificationCode(final VerifyEmailVerificationCodeDto.Request request) {
        EmailVerification emailVerification = emailVerificationMapper.getLatestVerification(request.email())
                .orElseThrow(() -> new GravyException(Status.BAD_REQUEST));

        LocalDateTime createdAt = emailVerification.getCreatedAt();
        String verificationCode = emailVerification.getVerificationCode();
        Long emailVerificationId = emailVerification.getId();
        UUID verificationPublicId = emailVerification.getPublicId();

        validateExpiredVerificationCode(createdAt);
        validateVerificationCode(request.verificationCode(), verificationCode);
        emailVerificationMapper.updateVerificationStatus(emailVerificationId, EmailVerificationStatus.VERIFIED);

        return new VerifyEmailVerificationCodeDto.Response(verificationPublicId);
    }

    private void validateExpiredVerificationCode(final LocalDateTime createdAt) {
        long compareRequestTime = Duration.between(createdAt, LocalDateTime.now()).getSeconds();
        if (compareRequestTime > MailConfiguration.POSSIBLE_REQUEST_TIME) {
            throw new GravyException(Status.EXPIRED_VERIFICATION_CODE);
        }
    }

    private void validateVerificationCode(final String requestedVerificationCode, final String verificationCode) {
        if (!Objects.equals(requestedVerificationCode, verificationCode)) {
            throw new GravyException(Status.VERIFICATION_CODE_MISMATCH);
        }
    }

    @Transactional(readOnly = true)
    public void validateDuplicateEmail(final String email) {
        boolean existsAlreadyEmail = userMapper.existsAlreadyEmail(email);
        if (existsAlreadyEmail) {
            throw new GravyException(Status.EXISTS_ALREADY_EMAIL);
        }
    }
}
