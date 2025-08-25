package kr.gravy.gravy.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
@Tag(name = "ErrorDocumentation", description = "API 에러 코드별 상세 해결 방법 및 가이드")
public class ErrorDocumentationController {

    @Operation(
            summary = "BAD_REQUEST - 잘못된 요청",
            description = """
                    **잘못된 요청입니다.**
                    
                    **가능한 원인:**
                    - **요청 형식 오류**
                    - 지원하지 않는 HTTP 메서드
                    - 잘못된 Content-Type
                    - 요청 경로 오류
                    
                    **해결 방법:**
                    1. **API 문서를 확인**하여 올바른 요청 형식을 사용하세요
                    2. HTTP 메서드와 헤더를 확인하세요
                    3. 요청 경로가 정확한지 확인하세요
                    """,
            responses = @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "잘못된 요청입니다.",
                                              "status": 400,
                                              "detail": "잘못된 요청입니다.",
                                              "instance": "/api/v1/unknown",
                                              "code": "bad_request",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/bad_request")
    public void bad_request() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "AUTH001 - 인증 실패",
            description = """
                    **사용자 인증에 실패했습니다.**
                    
                    **가능한 원인:**
                    - **잘못된 이메일 또는 비밀번호**
                    - 존재하지 않는 계정
                    - 비밀번호 오타
                    
                    **해결 방법:**
                    1. **이메일과 비밀번호를 다시 확인**하세요
                    2. 비밀번호 찾기를 통해 새 비밀번호를 설정하세요
                    """,
            responses = @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Authentication Failed",
                                              "status": 401,
                                              "detail": "사용자 인증에 실패했습니다",
                                              "instance": "/api/v1/auth/tokens",
                                              "code": "AUTH001",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/auth001")
    public void auth001() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "AUTH002 - 토큰을 찾을 수 없음",
            description = """
                    **요청하신 Refresh Token을 찾을 수 없습니다.**
                    
                    **가능한 원인:**
                    - 토큰이 만료됨
                    - 토큰이 무효화됨
                    - 잘못된 토큰
                    - 토큰이 이미 사용됨
                    
                    **해결 방법:**
                    1. 다시 로그인하여 새로운 토큰을 발급받으세요
                    2. 토큰 저장 방식을 확인하세요
                    3. 토큰 전송 방식을 확인하세요
                    """,
            responses = @ApiResponse(
                    responseCode = "401",
                    description = "토큰 인증 실패 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "TOKEN_NOT_FOUND_EXAMPLE",
                                    summary = "토큰을 찾을 수 없는 경우",
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Token Not Found",
                                              "status": 401,
                                              "detail": "토큰을 찾을 수 없습니다",
                                              "instance": "/auth/refresh",
                                              "code": "AUTH002",
                                              "timestamp": "2024-01-15T10:30:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/auth002")
    public void auth002() {
        // 문서화 전용 메서드 (실제 구현 없음)
    }

    @Operation(
            summary = "AUTH003 - 토큰 만료",
            description = """
                    **토큰의 유효 기간이 만료되었습니다.**
                    
                    **가능한 원인:**
                    - **토큰 유효기간 초과**
                    - 서버 시간 불일치
                    
                    **해결 방법:**
                    1. **새로운 토큰을 발급받으세요**
                    2. 자동 토큰 갱신 로직을 구현하세요
                    """,
            responses = @ApiResponse(
                    responseCode = "401",
                    description = "토큰 만료 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Token Expired",
                                              "status": 401,
                                              "detail": "토큰의 유효 기간이 만료되었습니다",
                                              "instance": "/api/v1/auth/tokens/reissue",
                                              "code": "AUTH003",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/auth003")
    public void auth003() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "USER001 - 사용자를 찾을 수 없음",
            description = """
                    **요청한 사용자 정보를 찾을 수 없습니다.**
                    
                    **가능한 원인:**
                    - **존재하지 않는 사용자 ID**
                    - 탈퇴한 계정
                    - 권한 없는 접근
                    
                    **해결 방법:**
                    1. **사용자 ID를 다시 확인**하세요
                    2. 로그인 상태 및 접근 권한을 확인하세요
                    """,
            responses = @ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없음 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "User Not Found",
                                              "status": 404,
                                              "detail": "사용자를 찾을 수 없습니다",
                                              "instance": "/api/v1/users/123",
                                              "code": "USER001",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/user001")
    public void user001() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "VERIFY004 - 이메일 이미 존재",
            description = """
                    **이미 사용 중인 이메일 주소입니다.**
                    
                    **가능한 원인:**
                    - **중복된 이메일**
                    - 탈퇴 후 재가입 시도
                    
                    **해결 방법:**
                    1. **다른 이메일 주소를 사용**하세요
                    2. 기존 계정으로 로그인을 시도해보세요
                    """,
            responses = @ApiResponse(
                    responseCode = "400",
                    description = "이메일 중복 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Email Already Exists",
                                              "status": 400,
                                              "detail": "다른 이메일을 사용해주세요.",
                                              "instance": "/api/v1/users/email/test@example.com/availability",
                                              "code": "VERIFY004",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/verify004")
    public void verify004() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "VERIFY001 - 인증번호 불일치",
            description = """
                    **입력하신 인증번호가 일치하지 않습니다.**
                    
                    **가능한 원인:**
                    - **잘못된 인증번호**
                    - **인증번호 만료** (3분 초과)
                    - 이미 사용된 인증번호
                    
                    **해결 방법:**
                    1. **인증번호를 정확히 입력**하세요 (6자리 숫자)
                    2. **새로운 인증번호를 재발송**받으세요
                    """,
            responses = @ApiResponse(
                    responseCode = "400",
                    description = "인증번호 불일치 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Verification Code Mismatch",
                                              "status": 400,
                                              "detail": "인증번호가 일치하지 않습니다",
                                              "instance": "/api/v1/email-verifications/status",
                                              "code": "VERIFY001",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/verify001")
    public void verify001() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "VERIFY002 - 이메일 인증 미완료",
            description = """
                    **이메일 인증이 완료되지 않았습니다.**
                    
                    **가능한 원인:**
                    - **이메일 인증을 하지 않음**
                    - 인증 과정 중단
                    - 만료된 인증 코드 사용
                    
                    **해결 방법:**
                    1. **이메일 인증을 완료**하세요
                    2. **새로운 인증 코드를 요청**하세요
                    """,
            responses = @ApiResponse(
                    responseCode = "400",
                    description = "이메일 인증 미완료 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Email Not Verified",
                                              "status": 400,
                                              "detail": "이메일 인증을 다시 진행해주세요.",
                                              "instance": "/api/v1/users",
                                              "code": "VERIFY002",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/verify002")
    public void verify002() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "VERIFY003 - 인증 코드 만료",
            description = """
                    **이메일 인증 코드가 만료되었습니다.**
                    
                    **가능한 원인:**
                    - **3분 시간 초과**
                    - 오래된 인증 코드 사용
                    
                    **해결 방법:**
                    1. **새로운 인증 코드를 요청**하세요
                    2. 3분 이내에 인증을 완료하세요
                    """,
            responses = @ApiResponse(
                    responseCode = "400",
                    description = "인증 코드 만료 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Verification Code Expired",
                                              "status": 400,
                                              "detail": "이메일 인증이 만료된 코드입니다.",
                                              "instance": "/api/v1/email-verifications",
                                              "code": "VERIFY003",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/verify003")
    public void verify003() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "VERIFY005 - 이메일 발송 실패",
            description = """
                    **이메일 발송에 실패했습니다.**
                    
                    **가능한 원인:**
                    - **서버 이메일 설정 오류**
                    - 네트워크 연결 문제
                    - 잘못된 이메일 주소
                    
                    **해결 방법:**
                    1. **이메일 주소를 다시 확인**하세요
                    2. 잠시 후 다시 시도하세요
                    3. 문제가 지속되면 관리자에게 문의하세요
                    """,
            responses = @ApiResponse(
                    responseCode = "500",
                    description = "이메일 발송 실패 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Email Send Failed",
                                              "status": 500,
                                              "detail": "이메일 발송에 실패하였습니다.",
                                              "instance": "/api/v1/email-verifications",
                                              "code": "VERIFY005",
                                              "timestamp": "2025-08-24T00:00:00"
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/verify005")
    public void verify005() {
        // 문서화 전용 메서드
    }

    @Operation(
            summary = "VALID001 - 입력값 검증 실패",
            description = """
                    **요청 데이터의 형식이 올바르지 않습니다.**
                    
                    **가능한 원인:**
                    - 필수 값 누락
                    - 형식 오류 (이메일, 전화번호 등)
                    - 길이 제한 초과
                    - 타입 불일치
                    
                    **해결 방법:**
                    1. 입력 형식을 확인하세요
                    2. 필수 필드를 모두 입력하세요
                    3. 길이 제한을 확인하세요
                    
                    **상세 오류 정보:**
                    응답의 `invalidFields`에서 구체적인 검증 오류를 확인할 수 있습니다.
                    """,

            responses = @ApiResponse(
                    responseCode = "400",
                    description = "입력값 검증 실패 시 응답 예시",
                    content = @Content(
                            mediaType = "application/problem+json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "type": "https://gravy.kr/swagger-ui/index.html?urls.primaryName=ErrorDocumentation",
                                              "title": "Validation Error",
                                              "status": 400,
                                              "detail": "입력값 검증에 실패했습니다",
                                              "instance": "/auth/signup",
                                              "code": "VALID001",
                                              "timestamp": "2024-01-15T10:30:00",
                                              "invalidFields": {
                                                "email": "올바른 이메일 형식이 아닙니다",
                                                "password": "비밀번호는 8자리 이상이어야 합니다"
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    @GetMapping("/valid001")
    public void valid001() {
        // 문서화 전용 메서드
    }
}
