package kr.gravy.gravy.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청입니다."),

    // === 인증 관련 ===
    AUTHENTICATION_FAILED(401, "AUTH001", "인증에 실패했습니다"),
    TOKEN_NOT_FOUND(401, "AUTH002", "토큰을 찾을 수 없습니다"),
    TOKEN_EXPIRED(401, "AUTH003", "토큰이 만료되었습니다"),

    // === 이메일 인증 관련 ===
    VERIFICATION_CODE_MISMATCH(400, "VERIFY001", "인증번호가 일치하지 않습니다"),
    EMAIL_NOT_VERIFIED(400, "VERIFY002", "이메일 인증을 다시 진행해주세요."),
    EXPIRED_VERIFICATION_CODE(400, "VERIFY003", "이메일 인증이 만료된 코드입니다."),
    EXISTS_ALREADY_EMAIL(400, "VERIFY004", "다른 이메일을 사용해주세요."),
    FAIL_SEND_MAIL(500, "VERIFY005", "이메일 발송에 실패하였습니다."),

    // === 사용자 관련 ===
    USER_NOT_FOUND(404, "USER001", "사용자를 찾을 수 없습니다");


    private final int httpStatusCode;
    private final String code;
    private final String message;


}
