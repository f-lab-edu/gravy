package kr.gravy.gravy.auth.service;

import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import kr.gravy.gravy.common.utils.GeneratorUtil;
import kr.gravy.gravy.auth.jwt.JWTUtil;
import kr.gravy.gravy.auth.dto.ReIssueAccessTokenDto;
import kr.gravy.gravy.auth.dto.UserLoginDto;
import kr.gravy.gravy.auth.dto.UserSignUpDto;
import kr.gravy.gravy.email.enumeration.EmailVerificationStatus;
import kr.gravy.gravy.auth.enumeration.Grade;
import kr.gravy.gravy.email.mapper.EmailVerificationMapper;
import kr.gravy.gravy.auth.mapper.RefreshTokenMapper;
import kr.gravy.gravy.auth.mapper.UserMapper;
import kr.gravy.gravy.email.vo.RefreshTokenVO;
import kr.gravy.gravy.auth.vo.SignUpVO;
import kr.gravy.gravy.auth.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        SignUpVO signUpVO = emailVerificationMapper.getEmailAndVerificationStatusCode(request.getVerificationPublicId())
                .orElseThrow(() -> new GravyException(Status.BAD_REQUEST));
        validateEmailVerifiedStatus(signUpVO.getStatus());
        emailVerificationMapper.updateVerificationStatus(signUpVO.getId(), EmailVerificationStatus.CONSUMED);

        UserVO userVO = UserVO.builder()
                .publicId(GeneratorUtil.generatePublicId())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(signUpVO.getEmail())
                .grade(Grade.BASIC)
                .build();

        userMapper.userSignUp(userVO);
    }

    private void validateEmailVerifiedStatus(final EmailVerificationStatus verificationStatus) {
        if (!Objects.equals(verificationStatus, EmailVerificationStatus.VERIFIED)) {
            throw new GravyException(Status.EMAIL_NOT_VERIFIED);
        }
    }

    @Transactional
    public UserLoginDto.Response userLogin(final UserLoginDto.Request request) {

        UserVO user = userMapper.getUserByEmail(request.getEmail().trim())
                .orElseThrow(() -> new GravyException(Status.USER_NOT_FOUND));

        user.validatePassword(passwordEncoder, request.getPassword());

        UUID userPublicId = user.getPublicId();
        String accessToken = jwtUtil.createAccessToken(userPublicId);
        String refreshToken = jwtUtil.createRefreshToken(userPublicId);

        Date refreshTokenExpiredDate = jwtUtil.getExpiration(refreshToken);
        RefreshTokenVO refreshTokenVO = RefreshTokenVO.builder()
                .userId(user.getId())
                .token(refreshToken)
                .expiredAt(refreshTokenExpiredDate)
                .build();

        refreshTokenMapper.insertRefreshToken(refreshTokenVO);

        return UserLoginDto.Response.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public ReIssueAccessTokenDto.Response reIssueAccessToken(final String requestedRefreshToken) {
        validateExpiredRefreshToken(requestedRefreshToken);

        UserVO user = refreshTokenMapper.getUserByRefreshToken(requestedRefreshToken)
                .orElseThrow(() -> new GravyException(Status.USER_NOT_FOUND));

        String accessToken = jwtUtil.createAccessToken(user.getPublicId());
        String refreshToken = jwtUtil.createRefreshToken(user.getPublicId());

        Date refreshTokenExpiredDate = jwtUtil.getExpiration(refreshToken);

        RefreshTokenVO refreshTokenVO = RefreshTokenVO.builder()
                .userId(user.getId())
                .token(refreshToken)
                .expiredAt(refreshTokenExpiredDate)
                .build();
        refreshTokenMapper.insertRefreshToken(refreshTokenVO);

        return ReIssueAccessTokenDto.Response.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void validateExpiredRefreshToken(String requestedRefreshToken) {
        Date refreshTokenExpiredAt = refreshTokenMapper.getRefreshTokenExpiredAt(requestedRefreshToken)
                .orElseThrow(() -> new GravyException(Status.TOKEN_NOT_FOUND));

        Date now = new Date();
        if (refreshTokenExpiredAt.before(now)) {
            throw new GravyException(Status.TOKEN_EXPIRED);
        }
    }
}
