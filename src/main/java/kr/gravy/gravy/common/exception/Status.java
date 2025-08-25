package kr.gravy.gravy.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.LocalDateTime;

import static kr.gravy.gravy.common.constants.SwaggerConstants.ERROR_DOCUMENTATION_URL;

@Getter
@RequiredArgsConstructor
public enum Status {

    BAD_REQUEST(400, "bad_request", "잘못된 요청입니다."),

    // === 인증 관련 ===
    AUTHENTICATION_FAILED(401, "auth001", "인증에 실패했습니다"),
    TOKEN_NOT_FOUND(401, "auth002", "토큰을 찾을 수 없습니다"),
    TOKEN_EXPIRED(401, "auth003", "토큰이 만료되었습니다"),

    // === 이메일 인증 관련 ===
    VERIFICATION_CODE_MISMATCH(400, "verify001", "인증번호가 일치하지 않습니다"),
    EMAIL_NOT_VERIFIED(400, "verify002", "이메일 인증을 다시 진행해주세요."),
    EXPIRED_VERIFICATION_CODE(400, "verify003", "이메일 인증이 만료된 코드입니다."),
    EXISTS_ALREADY_EMAIL(400, "verify004", "다른 이메일을 사용해주세요."),
    FAIL_SEND_MAIL(500, "verify005", "이메일 발송에 실패하였습니다."),

    // === 사용자 관련 ===
    USER_NOT_FOUND(404, "user001", "사용자를 찾을 수 없습니다"),

    // === 검증 관련 ===
    VALIDATION_FAILED(400, "valid001", "입력값 검증에 실패했습니다");


    private final int httpStatusCode;
    private final String code;
    private final String message;

    private URI docUri;
    private static URI fallbackUri;

    public static void initFallbackUri(String baseUrl) {
        fallbackUri = URI.create(baseUrl + ERROR_DOCUMENTATION_URL);
    }

    public void initDocUri(String baseUrl) {
        this.docUri = URI.create(baseUrl + ERROR_DOCUMENTATION_URL + code);
    }

    public URI getDocUri() {
        return docUri != null ? docUri : fallbackUri;
    }

    public ProblemDetail toProblemDetail(URI instance) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(httpStatusCode),
                message
        );

        // Problem Details 필드 설정
        problemDetail.setType(getDocUri());
        problemDetail.setInstance(instance);
        problemDetail.setProperty("code", code);
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }
}
