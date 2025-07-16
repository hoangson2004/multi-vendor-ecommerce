package hust.hoangson.auth.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .code(0)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> error(int code, String message) {
        return BaseResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}
