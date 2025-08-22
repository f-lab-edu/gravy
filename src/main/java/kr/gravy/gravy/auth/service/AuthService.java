package kr.gravy.gravy.auth.service;

import kr.gravy.gravy.auth.dto.ReissueAccessTokenDto;
import kr.gravy.gravy.auth.dto.UserLoginDto;
import kr.gravy.gravy.auth.dto.UserSignUpDto;
import kr.gravy.gravy.auth.entity.RefreshToken;
import kr.gravy.gravy.auth.entity.User;
import kr.gravy.gravy.auth.jwt.JWTUtil;
import kr.gravy.gravy.auth.mapper.RefreshTokenMapper;
import kr.gravy.gravy.auth.mapper.UserMapper;
import kr.gravy.gravy.auth.model.Grade;
import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import kr.gravy.gravy.common.utils.DateTimeUtil;
import kr.gravy.gravy.common.utils.GeneratorUtil;
import kr.gravy.gravy.email.entity.EmailVerification;
import kr.gravy.gravy.email.mapper.EmailVerificationMapper;
import kr.gravy.gravy.email.model.EmailVerificationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final EmailVerificationMapper emailVerificationMapper;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenMapper refreshTokenMapper;
    private final JWTUtil jwtUtil;


    @Transactional
    public void signUp(final UserSignUpDto.Request request) {
        EmailVerification emailVerification = emailVerificationMapper.getEmailAndVerificationStatusCode(request.verificationPublicId())
                .orElseThrow(() -> new GravyException(Status.BAD_REQUEST));
        validateEmailVerifiedStatus(emailVerification.getStatus());
        emailVerificationMapper.updateVerificationStatus(emailVerification.getId(), EmailVerificationStatus.CONSUMED);

        User user = User.builder()
                .publicId(GeneratorUtil.generatePublicId())
                .nickname(request.nickname())
                .password(passwordEncoder.encode(request.password()))
                .email(emailVerification.getEmail())
                .grade(Grade.BASIC)
                .build();

        userMapper.userSignUp(user);
    }

    private void validateEmailVerifiedStatus(final EmailVerificationStatus verificationStatus) {
        if (!Objects.equals(verificationStatus, EmailVerificationStatus.VERIFIED)) {
            throw new GravyException(Status.EMAIL_NOT_VERIFIED);
        }
    }

    @Transactional
    public UserLoginDto.Response userLogin(final UserLoginDto.Request request) {
        User user = userMapper.getUserByEmail(request.email().trim())
                .orElseThrow(() -> new GravyException(Status.USER_NOT_FOUND));

        user.validatePassword(passwordEncoder, request.password());

        UUID userPublicId = user.getPublicId();
        String accessToken = jwtUtil.createAccessToken(userPublicId);
        String refreshToken = jwtUtil.createRefreshToken(userPublicId);

        Date refreshTokenExpiredDate = jwtUtil.getExpiration(refreshToken);
        LocalDateTime refreshTokenExpiredAt = DateTimeUtil.convertToLocalDateTime(refreshTokenExpiredDate);

        RefreshToken newRefreshToken = RefreshToken.builder()
                .userId(user.getId())
                .token(refreshToken)
                .expiredAt(refreshTokenExpiredAt)
                .build();
        refreshTokenMapper.insertRefreshToken(newRefreshToken);

        return new UserLoginDto.Response(accessToken, refreshToken);
    }

    @Transactional
    public ReissueAccessTokenDto.Response reissueAccessToken(final String requestedRefreshToken) {
        validateExpiredRefreshToken(requestedRefreshToken);

        User user = refreshTokenMapper.getUserByRefreshToken(requestedRefreshToken)
                .orElseThrow(() -> new GravyException(Status.USER_NOT_FOUND));

        String accessToken = jwtUtil.createAccessToken(user.getPublicId());
        String refreshToken = jwtUtil.createRefreshToken(user.getPublicId());

        Date refreshTokenExpiredDate = jwtUtil.getExpiration(refreshToken);
        LocalDateTime refreshTokenExpiredAt = DateTimeUtil.convertToLocalDateTime(refreshTokenExpiredDate);
        RefreshToken refreshTokenVO = RefreshToken.builder()
                .userId(user.getId())
                .token(refreshToken)
                .expiredAt(refreshTokenExpiredAt)
                .build();
        refreshTokenMapper.insertRefreshToken(refreshTokenVO);

        return new ReissueAccessTokenDto.Response(accessToken, refreshToken);
    }

    private void validateExpiredRefreshToken(String requestedRefreshToken) {
        LocalDateTime refreshTokenExpiredAt = refreshTokenMapper.getRefreshTokenExpiredAt(requestedRefreshToken)
                .orElseThrow(() -> new GravyException(Status.TOKEN_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();
        if (refreshTokenExpiredAt.isBefore(now)) {
            throw new GravyException(Status.TOKEN_EXPIRED);
        }
    }
}
