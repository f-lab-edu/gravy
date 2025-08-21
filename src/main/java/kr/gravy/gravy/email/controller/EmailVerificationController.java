package kr.gravy.gravy.email.controller;

import jakarta.validation.Valid;
import kr.gravy.gravy.email.dto.SendEmailVerificationCodeDto;
import kr.gravy.gravy.email.dto.VerifyEmailVerificationCodeDto;
import kr.gravy.gravy.email.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/api/v1/email-verifications")
    public ResponseEntity<Void> sendEmailVerificationCode(
            @Valid @RequestBody SendEmailVerificationCodeDto.Request request) {
        emailVerificationService.sendEmailVerificationCode(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/v1/users/email/{email}/availability")
    public ResponseEntity<Void> validateDuplicateEmail(@PathVariable String email) {
        emailVerificationService.validateDuplicateEmail(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/api/v1/email-verifications/status")
    public ResponseEntity<VerifyEmailVerificationCodeDto.Response> verifyEmailVerificationCode(
            @Valid @RequestBody VerifyEmailVerificationCodeDto.Request request) {
        return ResponseEntity.status(HttpStatus.OK).body(emailVerificationService.verifyEmailVerificationCode(request));
    }

}
