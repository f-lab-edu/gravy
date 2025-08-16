package kr.gravy.gravy.controller;

import jakarta.validation.Valid;
import kr.gravy.gravy.dto.ReIssueAccessTokenDto;
import kr.gravy.gravy.dto.UserLoginDto;
import kr.gravy.gravy.dto.UserSignUpDto;
import kr.gravy.gravy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserSignUpDto.Request request) {
        userService.signUp(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserLoginDto.Response> userLogin(@Valid @RequestBody UserLoginDto.Request request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.userLogin(request));
    }

    @PostMapping("/user/reissue/access-token")
    public ResponseEntity<ReIssueAccessTokenDto.Response> reIssueAccessToken(
            @CookieValue(name = "refresh_token") String refreshToken) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.reIssueAccessToken(refreshToken));
    }
}
