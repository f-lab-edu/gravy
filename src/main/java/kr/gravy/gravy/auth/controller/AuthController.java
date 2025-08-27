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
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/api/v1/auth/tokens")
    public ResponseEntity<Void> userLogout(@CookieValue(name = CookieUtil.REFRESH_COOKIE) String refreshToken) {
        authService.userLogout(refreshToken);

        ResponseCookie deleteAccessCookie = cookieUtil.deleteCookieOfAccessToken();
        ResponseCookie deleteRefreshCookie = cookieUtil.deleteCookieOfRefreshToken();

        return ResponseEntity.status(HttpStatus.OK)
                .header("Set-Cookie", deleteAccessCookie.toString())
                .header("Set-Cookie", deleteRefreshCookie.toString())
                .build();
    }

    //TODO:: 사용자 정보 조회 및 로그인 여부 판별 API 만들기
    // Q. 사용자 로그인 여부를 확인하기 위한 API를 만드는 것이 일반적인가 ?
    // 흐름: 루트(/)페이지 접근 -> 로그인 여부 확인 -> 로그인된 사용자는 대시보드 버튼 보이게, or not: 로그인 버튼과 대시보드 버튼 -> 대시보드 버튼 누를 시 로그인 페이지로 이동
    @PostMapping("/api/v1/auth/test")
    public ResponseEntity<Void> checkAlreadyLogin() {
        System.out.println("----check----");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
