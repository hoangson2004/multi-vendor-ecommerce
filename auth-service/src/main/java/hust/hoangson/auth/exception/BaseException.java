package hust.hoangson.auth.exception;

import hust.hoangson.auth.response.BaseResponse;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final int errorCode;

    public BaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseResponse<?> toResponse() {
        return BaseResponse.error(errorCode, getMessage());
    }
}
