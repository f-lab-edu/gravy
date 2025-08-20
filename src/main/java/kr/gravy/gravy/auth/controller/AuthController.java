package kr.gravy.gravy.auth.controller;

import jakarta.validation.Valid;
import kr.gravy.gravy.auth.dto.ReIssueAccessTokenDto;
import kr.gravy.gravy.auth.dto.UserLoginDto;
import kr.gravy.gravy.auth.dto.UserSignUpDto;
import kr.gravy.gravy.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserSignUpDto.Request request) {
        authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserLoginDto.Response> userLogin(@Valid @RequestBody UserLoginDto.Request request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.userLogin(request));
    }

    @PostMapping("/user/reissue/access-token")
    public ResponseEntity<ReIssueAccessTokenDto.Response> reIssueAccessToken(
            @CookieValue(name = "refresh_token") String refreshToken) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.reIssueAccessToken(refreshToken));
    }
}
