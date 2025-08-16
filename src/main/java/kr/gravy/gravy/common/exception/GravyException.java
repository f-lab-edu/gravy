package kr.gravy.gravy.common.exception;

import lombok.Getter;

@Getter
public class GravyException extends RuntimeException {
    private final int httpStatusCode;
    private final String code;
    private final String message;

    public GravyException(Status status) {
        super(status.name());
        this.httpStatusCode = status.getHttpStatusCode();
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public GravyException(String message) {
        super(Status.BAD_REQUEST.name());
        this.httpStatusCode = Status.BAD_REQUEST.getHttpStatusCode();
        this.code = Status.BAD_REQUEST.getCode();
        this.message = message;
    }
}
