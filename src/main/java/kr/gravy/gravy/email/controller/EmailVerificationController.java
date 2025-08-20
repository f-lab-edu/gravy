package kr.gravy.gravy.email.controller;

import jakarta.validation.Valid;
import kr.gravy.gravy.email.service.EmailVerificationService;
import kr.gravy.gravy.email.dto.SendEmailVerificationCodeDto;
import kr.gravy.gravy.email.dto.ValidateDuplicateEmailDto;
import kr.gravy.gravy.email.dto.VerifyEmailVerificationCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/email/verification-code/send")
    public ResponseEntity<Void> sendEmailVerificationCode(
            @Valid @RequestBody SendEmailVerificationCodeDto.Request request) {
        emailVerificationService.sendEmailVerificationCode(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/email/duplicate")
    public ResponseEntity<Void> validateDuplicateEmail(
            @Valid @RequestBody ValidateDuplicateEmailDto.Request request) {
        emailVerificationService.validateDuplicateEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/email/verification-code/verify")
    public ResponseEntity<VerifyEmailVerificationCodeDto.Response> verifyEmailVerificationCode(
            @Valid @RequestBody VerifyEmailVerificationCodeDto.Request request) {
        return ResponseEntity.status(HttpStatus.OK).body(emailVerificationService.verifyEmailVerificationCode(request));
    }

}
