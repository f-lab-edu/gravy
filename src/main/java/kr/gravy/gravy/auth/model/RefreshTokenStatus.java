package kr.gravy.gravy.auth.model;

public enum RefreshTokenStatus {
    ACTIVE("사용 가능한 토큰"),
    REVOKED("무효화된 토큰"),
    EXPIRED("만료된 토큰");

    private final String status;

    RefreshTokenStatus(String status) {
        this.status = status;
    }
}
