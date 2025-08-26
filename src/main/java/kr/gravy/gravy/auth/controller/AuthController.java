package kr.gravy.gravy.auth.controller;

import jakarta.validation.Valid;
import kr.gravy.gravy.auth.dto.ReissueAccessTokenDto;
import kr.gravy.gravy.auth.dto.UserLoginDto;
import kr.gravy.gravy.auth.dto.UserSignUpDto;
import kr.gravy.gravy.auth.service.AuthService;
import kr.gravy.gravy.auth.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/api/v1/users")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserSignUpDto.Request request) {
        authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/api/v1/auth/tokens")
    public ResponseEntity<Void> userLogin(@Valid @RequestBody UserLoginDto.Request request) {
        UserLoginDto.Response loginResponse = authService.userLogin(request);

        ResponseCookie accessCookie = cookieUtil.createAccessTokenCookie(loginResponse.accessToken());
        ResponseCookie refreshCookie = cookieUtil.createRefreshTokenCookie(loginResponse.refreshToken());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Set-Cookie", accessCookie.toString())
                .header("Set-Cookie", refreshCookie.toString())
                .build();
    }

    @PostMapping("/api/v1/auth/tokens/reissue")
    public ResponseEntity<Void> reissueAccessToken(
            @CookieValue(name = CookieUtil.REFRESH_COOKIE) String refreshToken) {
        ReissueAccessTokenDto.Response reissueResponse = authService.reissueAccessToken(refreshToken);

        ResponseCookie accessCookie = cookieUtil.createAccessTokenCookie(reissueResponse.accessToken());
        ResponseCookie refreshCookie = cookieUtil.createRefreshTokenCookie(reissueResponse.refreshToken());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Set-Cookie", accessCookie.toString())
                .header("Set-Cookie", refreshCookie.toString())
                .build();
    }
}
